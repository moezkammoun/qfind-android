package qfind.com.qfindappandroid;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by dilee on 24-01-2018.
 */

public class MyApp extends Application {
    Locale myLocale;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences qfindPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String appLanguage = qfindPreferences.getString("AppLanguage", "en");
        if(appLanguage.equalsIgnoreCase("ar"))
        {
            Configuration configuration = getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale("ar"));
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            myLocale = new Locale("ar");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }else {

        }

    }
}
