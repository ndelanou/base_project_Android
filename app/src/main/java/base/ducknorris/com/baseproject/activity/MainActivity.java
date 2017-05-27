package base.ducknorris.com.baseproject.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import base.ducknorris.com.baseproject.R;
import base.ducknorris.com.baseproject.events.BaseEvent;
import base.ducknorris.com.baseproject.utils.EventBusManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AbstractBaseActivity {

    @BindView(R.id.hello)
    TextView hello;

    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        hello.setText("Hello Wolrd!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Picasso.with(this).load("http://i.ndtvimg.com/mt/movies/2012-07/david-hasselholf-new.jpg").into(image);
    }

    @OnClick(R.id.hello)
    public void onClick(){
        EventBusManager.BUS.post(new BaseEvent("BaseEvent Posted"));
    }

    @Subscribe
    public void onEvent(final BaseEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, event.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
