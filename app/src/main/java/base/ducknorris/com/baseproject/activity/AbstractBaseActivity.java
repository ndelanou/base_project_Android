package base.ducknorris.com.baseproject.activity;

import android.support.v7.app.AppCompatActivity;

import base.ducknorris.com.baseproject.utils.EventBusManager;

/**
 * Created by DuckN on 20/05/2017.
 */

public abstract class AbstractBaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        EventBusManager.BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusManager.BUS.unregister(this);
    }
}
