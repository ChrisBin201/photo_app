package com.example.photo_app.api;

import com.example.photo_app.model.LoginRequest;
import com.example.photo_app.model.LoginResponse;
import com.example.photo_app.model.Message;
import com.example.photo_app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    UserService authService = ApiClient.createService(UserService.class, null);

    @POST("login")
    Call<LoginRequest> checkLogin(@Body LoginResponse loginResponse);

    @POST("registry")
    Call<Message> checkRegister(@Body User User);

    @GET("getUserFromJWT")
    Call<User> getUserFromJWT();

    @GET("getUsersByFollowing/{id}")
    Call<List<User>> getUsersByFollowing(@Path("id") Long id);

    @GET("getUsersByFollowed/{id}")
    Call<List<User>> getUsersByFollowed(@Path("id") Long id);
}
