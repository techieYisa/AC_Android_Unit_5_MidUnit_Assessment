package nyc.c4q.mid_unit_5_practical_assessment.services.network;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import nyc.c4q.mid_unit_5_practical_assessment.model.User;
import nyc.c4q.mid_unit_5_practical_assessment.model.UserMapper;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.json.ResultJson;
import nyc.c4q.mid_unit_5_practical_assessment.services.network.json.UserJson;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Uses Retrofit to retrieve Users from the internet via the API
 * <p>
 * Created by charlie on 1/18/18.
 */

public class UserNetworkServiceRetrofit implements UserNetworkService {

    private static final String TAG = "UserNetworkServiceRetro";

    // Best practice is to save the base URL as a constant, as opposed to a mutable variable.
    private static final String BASE_URL = "https://randomuser.me/api/";

    @Override
    public List<User> getRandomlyGeneratedUsersFromApi(int numUsers) {

        // Have Retrofit generate a concrete implementation of the RandomUserApiService for us.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RandomUserApiService randomUserApiService = retrofit.create(RandomUserApiService.class);

        // Now use that service to create a Call object representing an HTTP GET request.
        Call<ResultJson> usersCall = randomUserApiService.getUsers(numUsers);

        // Execute that request synchronously using execute() because threading will be handled
        // by a different class. (If we did want asynchronous here we'd use enqueue() instead.)
        Response<ResultJson> response;

        try {
            response = usersCall.execute();
        } catch (IOException e) {
            Log.e(TAG, "getRandomlyGeneratedUsersFromApi: Unable to complete API call", e);
            e.printStackTrace();
            return null; // indicate failure with a null Users List
        }

        // Grab User POJOs from the response body.
        List<UserJson> userJsons = null;

        ResultJson result = response.body();
        if (result != null) {
            userJsons = result.getResults();
        }

        // Btw, GSON automatically converted the JSON data into an array of Java
        // objects for us. Thanks GSON!

        if (userJsons != null && userJsons.size() > 0) {

            Log.d(TAG,
                    String.format("onResponse: GET request to %s succeeded with %d results",
                            BASE_URL, userJsons.size()));

            // If request succeeds and is response is not empty,
            // convert to a List of instances of our User model class,
            // and pass those Users along to the callback.
            return UserMapper.from(userJsons);

        } else {
            Log.d(TAG, "onResponse: GET request returned zero users");
            return null;
        }
    }
}
