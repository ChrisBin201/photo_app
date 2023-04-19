package com.example.photo_app.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = getTokenFromSomeWhere();
        if (token != null) {
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
        return chain.proceed(original);
    }

    private String getTokenFromSomeWhere() {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        return prefs.getString("token", null); // trả về null khi token ko tìm thấy
    }
}
