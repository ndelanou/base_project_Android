package com.ducknorris.baseproject.utils.server;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.SocketException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

import com.ducknorris.baseproject.BaseApplication;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ndelanou on 17/05/2017.
 */

public class ServerHandler {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String SERVER_URL = "http://poutou.herokuapp.com/";

    public static ServerHandler INSTANCE = new ServerHandler();
    private OkHttpClient mClient = null;

    private ServerHandler() {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.Companion.getINSTANCE().getApplicationContext()));
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        // init OkHttpClient
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final CacheControl noCacheControl = new CacheControl.Builder().noStore().build();
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest;

                        newRequest = request.newBuilder()
                                .cacheControl(noCacheControl)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
    }

    public void get(final String url, final IServerCallback callback) throws ServerException {
        // Step 1: do a get request
        Request request = new Request.Builder()
                .url(SERVER_URL + url)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketException && e.getMessage().contains("ECONNRESET")) {
                    try {
                        // If error is a connection rest, try again
                        get(url, callback);
                    } catch (ServerException e1) {
                        // Silent catch, server exception will be given to callback
                    }
                } else {
                    callback.onFailure(new ServerException(e));
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    callback.onResponse(convertToJSONObjects(response, url));
                } catch (ServerException e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void post(final String url, JSONObject json, final IServerCallback callback) throws ServerException {
        // Step 1: do a post request
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(SERVER_URL + url)
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(new ServerException(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    callback.onResponse(convertToJSONObjects(response, url));
                } catch (ServerException e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private Collection<JSONObject> convertToJSONObjects(Response response, String url) throws ServerException {
        Collection<JSONObject> jsonObjects = new LinkedHashSet<JSONObject>();
        try {
            if (response == null || !response.isSuccessful()) {
                throw new ServerException("Invalid response at " + url + " : " + response.toString());
            }
            String stringResponse = response.body().string();
            if (stringResponse.isEmpty()) {
                throw new ServerException("Empty response at " + url);
            }
            try {
                if (stringResponse.trim().startsWith("[") || stringResponse.startsWith("\uFEFF")) {
                    JSONArray jsonArray = new JSONArray(stringResponse);
                    if (jsonArray.length() > 0 && jsonArray.get(0) instanceof JSONArray) {
                        JSONObject root = new JSONObject();
                        root.put("json", jsonArray);
                        jsonObjects.add(root);
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (jsonArray.get(i) instanceof JSONObject)
                                jsonObjects.add(jsonArray.getJSONObject(i));
                        }
                    }
                } else {
                    jsonObjects.addAll(convertResponseToSingleJson(stringResponse, url));
                }
            } catch (JSONException je) {
                jsonObjects.addAll(convertResponseToSingleJson(stringResponse, url));
            }
            return jsonObjects;
        } catch (Exception e) {
            throw new ServerException(e);
        }
    }

    private Collection<JSONObject> convertResponseToSingleJson(String response, String url) throws JSONException {
        Collection<JSONObject> jsonObjects = new LinkedHashSet<JSONObject>();
        try {
            if (response.indexOf("{") == 0 || response.indexOf("{") == 1) {
                jsonObjects.add(new JSONObject(response));
            } else {
                jsonObjects.add(new JSONObject("{json:" + response.trim() + "}"));
            }
        } catch (JSONException e) {
            jsonObjects.add(new JSONObject("{json:" + response + "}"));
        }
        return jsonObjects;
    }

    public OkHttpClient getOkHttpClient() {
        return mClient;
    }
}