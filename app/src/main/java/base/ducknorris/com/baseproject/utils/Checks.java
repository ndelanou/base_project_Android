package base.ducknorris.com.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import base.ducknorris.com.baseproject.BaseApplication;
import base.ducknorris.com.baseproject.R;


/**
 * Created by ndelanou on 17/05/2017.
 */

public class Checks {
    private static final long TOAST_DELAY = 2000;
    private static long lastToastShown;

    public static boolean hasInternet(boolean showToast, Activity context) {
        return hasInternet(showToast, context, R.string.requires_internet_message);
    }

    private static boolean hasInternet(boolean showToast, Activity context, int messageID) {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        boolean isOnline = activeNetworkInfo != null;
        if (isOnline) {
            isOnline = activeNetworkInfo.isConnected();
        }
        if (!isOnline && showToast && System.currentTimeMillis() - lastToastShown > TOAST_DELAY) {
            lastToastShown = System.currentTimeMillis();

            Toast.makeText(context, messageID, Toast.LENGTH_SHORT).show();
        }
        return isOnline;
    }
}
