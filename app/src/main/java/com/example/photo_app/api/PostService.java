package com.example.photo_app.api;

import com.example.photo_app.model.Post;
import com.example.photo_app.model.PostImgs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {
    @GET("get_feed/{user_id}")
    Call<ArrayList<Post>> getFeed(@Path("user_id") int user_id);

    @GET("get_post_imgs/{post_id}")
    Call<ArrayList<PostImgs>> getPostImgs(@Path("post_id") int post_id);
}
