package com.ducknorris.baseproject

import android.app.Application

/**
 * Created by DuckN on 20/05/2017.
 */

class BaseApplication : Application() {
    init {
        INSTANCE = this
    }

    companion object {
        var INSTANCE: BaseApplication? = null
    }
}
