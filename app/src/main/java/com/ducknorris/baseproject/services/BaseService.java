package com.ducknorris.baseproject.services;

import com.ducknorris.baseproject.utils.RetrofitManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by DuckN on 20/05/2017.
 */

public class BaseService {

    public static final BaseService INSTANCE = new BaseService();
    private final BaseRESTService mBaseRestService;

    private BaseService() {
        mBaseRestService = RetrofitManager.INSTANCE.create(BaseRESTService.class);
    }

    public void getBase() {
        mBaseRestService.getRequest().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Response ok
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Response failed
            }
        });
    }

    private interface BaseRESTService {
        @GET("baseget")
        Call<ResponseBody> getRequest();

        //@POST("user")
        //Call<ResponseBody> postUser(@Body User user);
    }
}
