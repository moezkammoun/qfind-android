package qfind.com.qfindappandroid.retrofitinstance;

import qfind.com.qfindappandroid.categorycontaineractivity.MainCategory;
import qfind.com.qfindappandroid.categoryfragment.SubCategory;
import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
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

    @GET("api/get-category")
    Call<MainCategory> getMainCategory(@Query("token") String token,@Query("language") int language);


    @GET("api/get-sub-category")
    Call<SubCategory> getSubCategory(@Query("token") String token, @Query("language") int language,@Query("category") int category);


}
