package qfind.com.qfindappandroid.retrofitinstance;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dilee on 25-01-2018.
 */

public class ApiClient {
    public static final String BASE_URL = "http://qfinds.intersmarthosting.in/";
    private static Retrofit retrofit = null;
    public static OkHttpClient.Builder client = new OkHttpClient.Builder();
    public static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();


    public static Retrofit getClient() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        client.connectTimeout(2, TimeUnit.MINUTES);
        client.readTimeout(2, TimeUnit.MINUTES);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
