package nyc.c4q.mid_unit_5_practical_assessment.services.network;

import nyc.c4q.mid_unit_5_practical_assessment.services.network.json.ResultJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit will generate a concrete class that implements this service. It will use the BASE_URL
 * we give it when we setup Retrofit, and combine that with the API endpoint listed below.
 * <p>
 * Created by charlie on 1/18/18.
 */

interface RandomUserApiService {
    @GET("?nat=us&inc=name,location,cell,email,dob,picture")
    Call<ResultJson> getUsers(@Query("results") int numUsers);
}
