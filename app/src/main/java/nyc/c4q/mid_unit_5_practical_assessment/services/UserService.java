package nyc.c4q.mid_unit_5_practical_assessment.services;

import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;


/**
 * Defines a new data type that can provide a list of User objects. Does not place any restrictions
 * on where the implementing class retrieves the Users from - could be database, internet, either.
 * <p>
 * Notice that it does imply this work will be done asynchronously on a background thread, so the
 * job of threading will be up to whichever concrete class implements this interface.
 * <p>
 * Created by charlie on 1/18/18.
 */
public interface UserService {

    /**
     * Retrieve users asynchronously and return the the provided callback object
     *
     * @param forceRefresh If true, clear local cache (database) and get a new set of randomly
     *                     generated users from the API. If false, OK to simply pull Users saved
     *                     locally, but may still need to call API if database is empty.
     * @param callback     Object that will receive Users when the asynchronous work is complete
     */
    void getUsersAsync(final boolean forceRefresh, final int numUsersToRetrieve,
                       final OnUsersReadyCallback callback);

    /**
     * Retrieve one specified user from the local database by it's primary key (ID) value.
     *
     * @param userId   Unique identifier of the selected User (source of ID values is the database's
     *                 primary key values)
     * @param callback Object that will receive Users when the asynchronous work is complete
     */
    void getSingleUserAsync(final int userId, final OnSingleUserReadyCallback callback);

    /**
     * Callback object to receive asynchronously retrieved Users
     */
    interface OnUsersReadyCallback {
        void onUsersReady(final List<User> users);
    }

    /**
     * Callback object to receive a single asynchronously retrieved User
     */
    interface OnSingleUserReadyCallback {
        void onUserReady(final User user);
    }
}
