package nyc.c4q.mid_unit_5_practical_assessment.services;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;
import nyc.c4q.mid_unit_5_practical_assessment.services.db.UserDbService;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.UserNetworkService;


/**
 * This is a concrete class that can provide a list of User objects.
 * As the name implies, it first tries to retrieve any locally stored Users (from local database),
 * and if none are present, then it retrieves from remote source (network/API call).
 * <p>
 * Created by charlie on 1/18/18.
 */

public class UserServiceDbFirstThenNetwork implements UserService {

    private static final String TAG = "UserServiceDbFirstThenN";

    private final UserDbService userDbService;
    private final UserNetworkService userNetworkService;

    public UserServiceDbFirstThenNetwork(UserDbService userDbService,
                                         UserNetworkService userNetworkService) {
        this.userDbService = userDbService;
        this.userNetworkService = userNetworkService;
    }

    @Override
    public void getUsersAsync(boolean forceRefresh, int numUsersToRetrieve,
                              OnUsersReadyCallback callback) {
        new LoadUsersAsyncTask(numUsersToRetrieve, userDbService, userNetworkService, callback)
                .execute(forceRefresh);
    }

    @Override
    public void getSingleUserAsync(int userId, OnSingleUserReadyCallback callback) {
        new LoadSingleUserAsyncTask(userDbService, callback)
                .execute(userId);
    }

    //----------------------------------------------------------------------------------------
    // Use an AsyncTask to load Users from the database first, resorting to the API if the
    // database is empty.
    //----------------------------------------------------------------------------------------
    private static class LoadUsersAsyncTask extends AsyncTask<Boolean, Void, List<User>> {

        private final int numUsersToRetrieve;
        private final UserDbService userDbService;
        private final UserNetworkService userNetworkService;
        private final OnUsersReadyCallback callback;

        LoadUsersAsyncTask(int numUsersToRetrieve, UserDbService userDbService,
                           UserNetworkService userNetworkService, OnUsersReadyCallback callback) {
            this.numUsersToRetrieve = numUsersToRetrieve;
            this.userDbService = userDbService;
            this.userNetworkService = userNetworkService;
            this.callback = callback;
        }

        @Override
        protected List<User> doInBackground(Boolean... booleans) {
            // If the user has requested a refresh, we need to clear the local database first,
            // which will then force a call to the API after the subsequent call to the database
            // comes up empty. Pass this to execute so it is passed here.
            if (booleans[0]) {
                userDbService.clearUsers();
            }

            // First, try the local database. If it returns users, no need for network call.
            Log.d(TAG, "doInBackground: Checking local database for users");
            List<User> users = userDbService.getUsers();

            if (users == null || users.size() == 0) {

                Log.d(TAG, "doInBackground: database empty, making network call");

                // Second, if database was empty, then make a network call to get users from the API.
                users = userNetworkService.getRandomlyGeneratedUsersFromApi(numUsersToRetrieve);

                if (users == null || users.size() == 0) {
                    Log.d(TAG, "doInBackground: Zero users returned by API");
                } else {

                    // Save retrieved users to the database, which gives them each an ID in the form of
                    // the database primary key value, then re-pull from database to have those IDs.
                    if (!userDbService.saveUsers(users)) {
                        Log.d(TAG, "doInBackground: Unable to save users to database");
                    }

                    users = userDbService.getUsers();
                }
            }

            return users; // This sends the users over to onPostExecute()
        }

        @Override
        protected void onPostExecute(List<User> users) {
            // Back on the main thread, return the list of users to the original caller.
            callback.onUsersReady(users);
        }
    }

    //----------------------------------------------------------------------------------------
    // Use an AsyncTask to load a single User from the database.
    //----------------------------------------------------------------------------------------
    private static class LoadSingleUserAsyncTask extends AsyncTask<Integer, Void, User> {

        private final UserDbService userDbService;
        private final OnSingleUserReadyCallback callback;

        LoadSingleUserAsyncTask(UserDbService userDbService,
                                OnSingleUserReadyCallback callback) {
            this.userDbService = userDbService;
            this.callback = callback;
        }

        @Override
        protected User doInBackground(Integer... integers) {
            // Pass the user ID to execute() which passes it to here
            return userDbService.getUserById(integers[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            // Back on the main thread, return the User to the original caller.
            callback.onUserReady(user);
        }
    }
}
