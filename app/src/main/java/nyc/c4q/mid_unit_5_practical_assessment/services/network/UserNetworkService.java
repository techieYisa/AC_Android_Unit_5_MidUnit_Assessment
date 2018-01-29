package nyc.c4q.mid_unit_5_practical_assessment.services.network;

import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;

/**
 * This defines what a network service must be able to do for this app. The reason to make the
 * interface is so that we have the freedom to implement using whatever networking library we like
 * (OkHttp, Retrofit, Volley, etc.) without having to change the rest of our code base.
 * <p>
 * Note that this interface assumes these operations will be done synchronously, meaning threading
 * must be handled before these methods are called. In this app, it will be handled by the
 * class that implements the UserService interface.
 * <p>
 * Created by charlie on 1/19/18.
 */

public interface UserNetworkService {

    /**
     * Calls the randomuser.me API and retrieves the specified number of randomly generated fake users
     *
     * @param numUsers is the quantity of fake users to be randomly generated
     * @return a List of newly generated users, or null if API call failed
     */
    List<User> getRandomlyGeneratedUsersFromApi(int numUsers);
}
