package com.ducknorris.baseproject.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by ndelanou on 17/05/2017.
 */

public final class EventBusManager {
    public static Bus BUS = new Bus(ThreadEnforcer.ANY);
}
