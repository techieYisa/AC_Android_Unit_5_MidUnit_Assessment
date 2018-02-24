package nyc.c4q.mid_unit_5_practical_assessment.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import nyc.c4q.mid_unit_5_practical_assessment.R;
import nyc.c4q.mid_unit_5_practical_assessment.ui.detail.UserDetailFragment;
import nyc.c4q.mid_unit_5_practical_assessment.ui.list.UserListFragment;

public class ViewUsersActivity extends AppCompatActivity
        implements UserListFragment.OnUserSelectedListener {

    private static final String LIST_FRAGMENT_TAG = "list_fragment";
    private static final String DETAIL_FRAGMENT_TAG = "detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add an instance of the user list fragment to our fragment container,
        // but only if the back stack is empty, otherwise let the fragment manager recreate it
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {

            // Use replace() rather than add() so we don't accidentally get two overlapping
            // copies of the same fragment after a device rotation.
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, UserListFragment.newInstance(), LIST_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.force_refresh_menu_item:
                // Tell the user list fragment to do a refresh & load a new set of users from the API
                UserListFragment userListFragment = (UserListFragment) getSupportFragmentManager()
                        .findFragmentByTag(LIST_FRAGMENT_TAG);
                userListFragment.clearDatabaseAndRefreshFromNetwork();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUserSelected(int userId) {
        // Note that it's important to add this transaction to the back stack so that the back
        // button will navigate from "detail" fragment back to "list" fragment, rather than
        // exit the app
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, UserDetailFragment.newInstance(userId), DETAIL_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        // Note that we use add() instead of replace() so that when the user presses "back" the
        // app won't have to recreate the list fragment because it will still be there underneath.
        // This requires setting the detail fragment's background color to opaque so they're not overlaid.
    }
}
