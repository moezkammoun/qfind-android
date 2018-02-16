package qfind.com.qfindappandroid.retrofitinstance;

import qfind.com.qfindappandroid.categorycontaineractivity.MainCategory;
import qfind.com.qfindappandroid.categoryfragment.ServiceProviderList;
import qfind.com.qfindappandroid.categoryfragment.SubCategory;
import qfind.com.qfindappandroid.homeactivty.QFindOfTheDayDetails;
import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
import qfind.com.qfindappandroid.informationFragment.ServiceProviderDataResponse;
import qfind.com.qfindappandroid.predictiveSearch.SearchResultsResponse;
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
    Call<MainCategory> getMainCategory(@Query("token") String token, @Query("language") int language);


    @GET("api/get-sub-category")
    Call<SubCategory> getSubCategory(@Query("token") String token, @Query("language") int language,
                                     @Query("category") int category);


    @GET("api/get-service-provider-inner")
    Call<ServiceProviderDataResponse> getServiceProviderData(@Query("token") String token, @Query("language") int language,
                                                             @Query("service") Integer serviceId, @Query("device_id") String deviceId);


    @GET("api/get-service-provider")
    Call<ServiceProviderList> getListOfServiceProvider(@Query("token") String token, @Query("language") int language,
                                                       @Query("category") int category, @Query("limit") int limit,
                                                        @Query("offset") int offset);

    @GET("api/search-result")
    Call<SearchResultsResponse> getSearchResults(@Query("token") String token,
                                                 @Query("search_key") String searchKey,
                                                 @Query("language") Integer language,
                                                 @Query("search_type") Integer searchType);

}

