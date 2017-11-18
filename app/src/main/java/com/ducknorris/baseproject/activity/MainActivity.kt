package com.ducknorris.baseproject.activity

import android.os.Bundle
import android.widget.Toast

import com.squareup.otto.Subscribe
import com.squareup.picasso.Picasso

import com.ducknorris.baseproject.R
import com.ducknorris.baseproject.events.BaseEvent
import com.ducknorris.baseproject.utils.EventBusManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hello_tv.text = "Hello Wolrd!"
    }

    override fun onResume() {
        super.onResume()
        Picasso.with(this).load("http://i.ndtvimg.com/mt/movies/2012-07/david-hasselholf-new.jpg").into(image_iv)

        image_iv.setOnClickListener {
            EventBusManager.BUS.post(BaseEvent("BaseEvent Posted"))
        }
    }

    @Subscribe
    fun onEvent(event: BaseEvent) {
        runOnUiThread { Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show() }
    }
}
