package nyc.c4q.mid_unit_5_practical_assessment.ui.list;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.R;
import nyc.c4q.mid_unit_5_practical_assessment.model.User;
import nyc.c4q.mid_unit_5_practical_assessment.services.UserService;
import nyc.c4q.mid_unit_5_practical_assessment.services.UserServiceDbFirstThenNetwork;
import nyc.c4q.mid_unit_5_practical_assessment.services.db.UserDbService;
import nyc.c4q.mid_unit_5_practical_assessment.services.db.UserDbServiceSqlite;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.UserNetworkService;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.UserNetworkServiceRetrofit;

/**
 * A fragment representing a list of Users.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnUserSelectedListener}
 * interface.
 */
public class UserListFragment extends Fragment {

    private static final int NUM_USERS_TO_DISPLAY = 20;
    private static final int DEFAULT_NUM_COLUMNS = 2;

    private OnUserSelectedListener listener;
    private UserService userService;
    private UserRvAdapter adapter;
    private ProgressBar progressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    public static UserListFragment newInstance() {
        // No arguments needed (if we had some, we'd put them in the arguments bundle here)
        return new UserListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the userService object that will provide the list of Users this fragment will display
        // (note - best practice would be to use dependency injection rather than
        // instantiate these services here, but that's a more advanced topic)
        UserDbService userDbService = UserDbServiceSqlite.getInstance(getContext());
        UserNetworkService userNetworkService = new UserNetworkServiceRetrofit();
        userService = new UserServiceDbFirstThenNetwork(userDbService, userNetworkService);

        // This will make sure onCreateOptionsMenu() is called
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        progressBar = view.findViewById(R.id.user_list_progress_bar);

        // Set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.user_recycler_view);

        // Users have not been retrieved yet, so pass an empty List to the adapter
        adapter = new UserRvAdapter(new ArrayList<User>(), listener);
        recyclerView.setAdapter(adapter);

        int numColumns = DEFAULT_NUM_COLUMNS;

        // Get screen width in order to determine how many columns our grid should have
        if (getActivity() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            // Let's go with 1 column for every 500 pixels of screen width
            numColumns = width / 500; // Remember that integer division truncates any remainder!
        }

        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), numColumns));

        // Retrieve the users, and pass them along to the adapter when ready
        progressBar.setVisibility(View.VISIBLE);
        userService.getUsersAsync(false, NUM_USERS_TO_DISPLAY, new UserService.OnUsersReadyCallback() {
            @Override
            public void onUsersReady(List<User> users) {
                updateScreenWithUser(users); // This hides the progress bar again when done
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Make sure the refresh option is visible when the user list fragment is on the screen
        MenuItem refreshMenuItem = menu.findItem(R.id.force_refresh_menu_item);
        if (refreshMenuItem != null) {
            refreshMenuItem.setVisible(true);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserSelectedListener) {
            listener = (OnUserSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUserSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This method will use the UserService to fetch a new set of User objects from the API,
     * and replace the old Users in the database with the new Users.
     */
    public void clearDatabaseAndRefreshFromNetwork() {

        // Check for network connectivity first
        if (getActivity() != null) {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;
            if (networkInfo != null && networkInfo.isConnected()) {

                // First, show the progress bar
                progressBar.setVisibility(View.VISIBLE);

                // The TRUE argument below is what will force the UserService to clear the local
                // database and fetch some new users from the API.
                userService.getUsersAsync(true, NUM_USERS_TO_DISPLAY, new UserService.OnUsersReadyCallback() {
                    @Override
                    public void onUsersReady(List<User> users) {
                        updateScreenWithUser(users);
                    }
                });
            } else {
                // Notify user they are not connected to the internet
                Toast.makeText(getContext(), getString(R.string.no_internet_msg),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    // Helper method to keep code D.R.Y.
    private void updateScreenWithUser(List<User> users) {
        if (users == null || users.size() == 0) {
            if (getActivity() != null) {
                Toast.makeText(getContext(), getString(R.string.zero_users_returned_msg),
                        Toast.LENGTH_LONG).show();
            }
        } else {

            // When the list of Users is finally ready, pass it to the adapter
            adapter.updateUsers(users);
        }

        // Lastly, hide the progress bar
        progressBar.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnUserSelectedListener {
        void onUserSelected(int userId);
    }
}
