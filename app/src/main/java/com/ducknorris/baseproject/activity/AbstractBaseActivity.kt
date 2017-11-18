package com.ducknorris.baseproject.activity

import android.support.v7.app.AppCompatActivity

import com.ducknorris.baseproject.utils.EventBusManager

/**
 * Created by DuckN on 20/05/2017.
 */

abstract class AbstractBaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        EventBusManager.BUS.register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBusManager.BUS.unregister(this)
    }
}
