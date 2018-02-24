package nyc.c4q.mid_unit_5_practical_assessment.ui.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.mid_unit_5_practical_assessment.R;
import nyc.c4q.mid_unit_5_practical_assessment.model.User;
import nyc.c4q.mid_unit_5_practical_assessment.services.UserService;
import nyc.c4q.mid_unit_5_practical_assessment.services.UserServiceDbFirstThenNetwork;
import nyc.c4q.mid_unit_5_practical_assessment.services.db.UserDbService;
import nyc.c4q.mid_unit_5_practical_assessment.services.db.UserDbServiceSqlite;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.UserNetworkService;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.UserNetworkServiceRetrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetailFragment extends Fragment {

    private static final String ARG_SELECTED_USER_ID = "selected_user_id";

    private int selectedUserId;
    private UserService userService;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedUserId is the ID value for the selected user to be displayed
     * @return A new instance of fragment UserDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetailFragment newInstance(int selectedUserId) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SELECTED_USER_ID, selectedUserId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedUserId = getArguments().getInt(ARG_SELECTED_USER_ID);
        }

        // Set up the userService object that will provide the User this fragment will display
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        // Get the selected user from the database
        userService.getSingleUserAsync(selectedUserId, new UserService.OnSingleUserReadyCallback() {
            @Override
            public void onUserReady(User user) {
                // Display that user's info
                ImageView imageView = view.findViewById(R.id.user_detail_image);
                Picasso.with(getContext()).load(user.getLargeImageUrl()).into(imageView);

                TextView name = view.findViewById(R.id.user_detail_name);
                name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

                TextView email = view.findViewById(R.id.user_detail_email);
                email.setText(user.getEmail());

                TextView cell = view.findViewById(R.id.user_detail_cell);
                cell.setText(user.getCell());

                TextView address = view.findViewById(R.id.user_detail_address);
                address.setText(String.format("%s\n%s,%s %s", user.getStreet(),
                        user.getCity(), user.getState(), user.getPostCode()));

                TextView dob = view.findViewById(R.id.user_detail_dob);
                dob.setText(user.getDob());
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Make sure the refresh option is hidden when the user detail fragment is on the screen
        MenuItem refreshMenuItem = menu.findItem(R.id.force_refresh_menu_item);
        if (refreshMenuItem != null) {
            refreshMenuItem.setVisible(false);
        }
    }
}
