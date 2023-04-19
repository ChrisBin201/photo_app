package com.example.photo_app.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private Context context;

    public ApiClient(Context context) {
        this.context = context;
    }

    private static final String BASE_URL = "http://192.168.1.4:8080/api/";

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static <T> T createService(Class<T> serviceClass, Context context) {
        if (context != null) {
            httpClientBuilder.addInterceptor(new HeaderInterceptor(context));
        }
        OkHttpClient httpClient = httpClientBuilder.build();
        retrofitBuilder.client(httpClient);
        retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

}
