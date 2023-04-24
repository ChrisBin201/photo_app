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
import retrofit2.http.Path;

public interface UserService {

    UserService authService = ApiClient.createService(UserService.class, null);

    @POST("login")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);

    @POST("registry")
    Call<Message> checkRegister(@Body User User);

    @GET("getUserFromJWT")
    Call<User> getUserFromJWT();

    @GET("getUsersByFollowing/{userId}")
    Call<List<Integer>> getUsersByFollowing(@Path("userId") int userId);

    @GET("getUsersByFollowed")
    Call<List<User>> getUsersByFollowed();

    @PUT("updateUser")
    Call<Message> updateUser(@Body User user);

    @GET("getUsersByKeyword/{keyword}")
    Call<List<User>> getUsersByKeyword(@Path("keyword") String keyword);

    @POST("follow/{id}")
    Call<Message> follow(@Path("id") Long id);

    @POST("unfollow/{id}")
    Call<Message> unfollow(@Path("id") Long id);

    @GET("getFollowing/{id}")
    Call<List<User>> getFollowing(@Path("id") Long id);

    @GET("getFollowed/{id}")
    Call<List<User>> getFollowed(@Path("id") Long id);

    @GET("checkFollow/{id}")
    Call<Message> checkFollow(@Path("id") Long id);

    @GET("getUserById/{id}")
    Call<User> getUserById(@Path("id") Long id);
}
