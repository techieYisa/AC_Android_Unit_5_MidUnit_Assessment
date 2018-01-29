package nyc.c4q.mid_unit_5_practical_assessment.services.db;

import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;

/**
 * This defines what a database service must be able to do for this app. The reason to make the
 * interface is so that we have the freedom to implement using whatever database tool we like
 * (SQLiteOpenHelper, Room, Realm, etc.) without having to change the rest of our code base.
 * <p>
 * Note that this interface assumes these operations will be done synchronously, meaning threading
 * must be handled before these methods are called. In this app, it will be handled by the
 * class that implements the UserService interface.
 * <p>
 * Created by charlie on 1/19/18.
 */

public interface UserDbService {

    /**
     * Saves a User to the database
     *
     * @param user is the User to be saved
     * @return True if saved successfully, else False (not absolutely necessary, but best practice)
     */
    boolean saveUser(User user);

    /**
     * Saves a List of Users to the database
     *
     * @param users is the List of Users to be saved
     * @return True if all users saved successfully, else False (not absolutely necessary, but good practice)
     */
    boolean saveUsers(List<User> users);

    /**
     * Retrieve all Users in the database
     *
     * @return a List of Users present in the database
     */
    List<User> getUsers();

    /**
     * Returns a single user whose primary key value matches the ID provided
     *
     * @param id is the primary key value of the desired User
     * @return a User if id is found in database, else null
     */
    User getUserById(int id);

    /**
     * Deletes all users from the local database
     */
    void clearUsers();
}
