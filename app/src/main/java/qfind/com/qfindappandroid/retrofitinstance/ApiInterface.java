package qfind.com.qfindappandroid.retrofitinstance;

import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dilee on 25-01-2018.
 */

public interface ApiInterface {
    @GET("api-clients/oauth-token")
    Call<RegistrationDetails> getAccessToken(@Query("clientid") String clientID,@Query("clientsecret") String clientSecret);

}
