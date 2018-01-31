package qfind.com.qfindappandroid;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by dilee on 18-01-2018.
 */

public class Util {
    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static int categoryPageStatus = 1;
}
