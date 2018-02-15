package qfind.com.qfindappandroid;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.squareup.leakcanary.LeakCanary;

import java.util.Locale;

import qfind.com.qfindappandroid.homeactivty.RegistrationDetails;
import qfind.com.qfindappandroid.retrofitinstance.ApiClient;
import qfind.com.qfindappandroid.retrofitinstance.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dilee on 24-01-2018.
 */

public class MyApp extends Application {
    Locale myLocale;
    private final String clientId = "80581B4C-C060-D166-7893-A4424C15A63D";
    private final String clientSecret = "0488AFF2-BCE0-BC87-F614-10F055107FEB";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
//        // Normal app init code...

        SharedPreferences qfindPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int appLanguage = qfindPreferences.getInt("AppLanguage", 1);
        if (appLanguage == 2) {
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale("ar"));
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            myLocale = new Locale("ar");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        } else {

        }
        if (!qfindPreferences.getBoolean("AuthTokenStatus", false)) {
            registerTheApp();
        }


    }

    public void registerTheApp() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RegistrationDetails> call = apiService.getAccessToken(clientId, clientSecret);
        call.enqueue(new Callback<RegistrationDetails>() {
            @Override
            public void onResponse(Call<RegistrationDetails> call, Response<RegistrationDetails> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        RegistrationDetails registrationDetails = response.body();
                        if (registrationDetails.getCode().equals("200")) {
                            SharedPreferences qfindPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = qfindPreferences.edit();
                            editor.putString("AccessToken", registrationDetails.getAccessToken());
                            editor.putBoolean("AuthTokenStatus",true);
                            editor.commit();
                        } else {
                            Util.showToast(getResources().getString(R.string.un_authorised), getApplicationContext());
                        }
                    }

                } else {
                    Util.showToast(getResources().getString(R.string.error_in_connecting), getApplicationContext());

                }

            }

            @Override
            public void onFailure(Call<RegistrationDetails> call, Throwable t) {
                Util.showToast(getResources().getString(R.string.check_network), getApplicationContext());

            }
        });
    }
}
