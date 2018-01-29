package qfind.com.qfindappandroid.retrofitinstance;

import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
import qfind.com.qfindappandroid.informationFragment.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dilee on 25-01-2018.
 */

public interface ApiInterface {
    @GET("api-clients/oauth-token")
    Call<RegistrationDetails> getAccessToken(@Query("clientid") String clientID, @Query("clientsecret") String clientSecret);

    @GET("api/q-find")
    Call<QFindOfTheDayDetails> getQFindOfTheDay(@Query("token") String token);

    @GET("api/get-service-provider-inner")
    Call<ApiResponse> getServiceProviderData(@Query("token") String token,
                                             @Query("language") Integer language,
                                             @Query("service") Integer serviceId,
                                             @Query("device_id") String deviceId);
}