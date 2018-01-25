package qfind.com.qfindappandroid.retrofitinstance;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dilee on 25-01-2018.
 */

public class ApiClient {
    public static final String BASE_URL = "http://qfinds.intersmarthosting.in/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
