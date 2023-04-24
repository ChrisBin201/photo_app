package com.example.photo_app.api;

import com.example.photo_app.model.User;
import com.example.photo_app.model.call.LoginRequest;
import com.example.photo_app.model.call.LoginResponse;
import com.example.photo_app.model.call.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    UserService authService = ApiClient.createService(UserService.class, null);

    @POST("login")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);

    @POST("registry")
    Call<Message> checkRegister(@Body User User);

    @GET("getUserFromJWT")
    Call<User> getUserFromJWT();

    @GET("getUsersByFollowing")
    Call<List<User>> getUsersByFollowing();

    @GET("getUsersByFollowed")
    Call<List<User>> getUsersByFollowed();

    @PUT("updateUser")
    Call<Message> updateUser(@Body User user);
}
