package com.example.photo_app.api;

import android.content.Intent;

import com.example.photo_app.model.Post;
import com.example.photo_app.model.PostImgs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostApiClient {
    private  final PostService postService;

    public PostApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        postService = retrofit.create(PostService.class);
    }

    public void getFeed(int userId, Callback<ArrayList<Post>> callback) {
        Call<ArrayList<Post>> getFeed = postService.getFeed(userId);
        getFeed.enqueue(callback);
    }

    public void getPostImgs(int postId, Callback<ArrayList<PostImgs>> callback) {
        Call<ArrayList<PostImgs>> getPostImgs = postService.getPostImgs(postId);
        getPostImgs.enqueue(callback);
    }

    public void uploadPost(Map<String, Object> body, Callback<Post> callback) {

        Call<Post> uploadPost = postService.uploadPost(body);
        uploadPost.enqueue(callback);
    }

}
