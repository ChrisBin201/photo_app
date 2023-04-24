package com.example.photo_app.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {
    @GET("api/get_feed")
    Call<ArrayList<Integer>> getFeed(int userid);
}
