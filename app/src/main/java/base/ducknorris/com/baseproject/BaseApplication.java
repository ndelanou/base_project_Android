package base.ducknorris.com.baseproject;

import android.app.Application;

/**
 * Created by DuckN on 20/05/2017.
 */

public class BaseApplication extends Application {

    public static BaseApplication INSTANCE;

    public BaseApplication() {INSTANCE = this;}
}
