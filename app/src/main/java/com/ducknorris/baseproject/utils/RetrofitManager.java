package com.ducknorris.baseproject.utils;

import com.ducknorris.baseproject.utils.server.ServerHandler;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ndelanou on 17/05/2017.
 */

public final class RetrofitManager {
    public static Retrofit INSTANCE = initializeRetrofit();
    static final long REQUEST_TIME_1_SECOND =   1000;
    static final long REQUEST_TIME_5_SECONDS =  5000;
    static final long REQUEST_TIME_30_SECONDS = 30000;
    static final long REQUEST_TIME_1_HOUR =     3600000;
    static final long REQUEST_TIME_24_HOURS =   86400000;

    private static Retrofit initializeRetrofit() {
        return new Retrofit.Builder()
                .client(ServerHandler.INSTANCE.getOkHttpClient())
                .baseUrl(ServerHandler.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(GSONManager.INSTANCE))
                .build();
    }
}
