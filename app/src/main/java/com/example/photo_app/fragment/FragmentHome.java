package com.example.photo_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.R;
import com.example.photo_app.api.PostService;
import com.example.photo_app.api.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentHome extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int userId = 1;
        ArrayList<Integer> followingIds = new ArrayList<>();
        // call get api to retrieve following ids using retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService postService = retrofit.create(PostService.class);
//        Call<ArrayList<Integer>> call = postService.getFeed(userId);
//        call.enqueue(new Callback<ArrayList<Integer>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
//                JsonObject json = new JsonObject();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
//
//            }
//        });



//        // get following ids from server
//        UserService userService = retrofit.create(UserService.class);
//        Call<List<Integer>> call = userService.getUsersByFollowing(userId);
//        call.enqueue(new Callback<List<Integer>>() {
//            @Override
//            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
//                if (response.isSuccessful()) {
//                    JsonObject json = new JsonObject();
//                    JsonArray followingListJson = json.getAsJsonArray("following_list");
//                    List<Integer> followingList = new ArrayList<>();
//                    for (JsonElement followingId : followingListJson) {
//                        followingList.add(followingId.getAsInt());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Integer>> call, Throwable t) {
//            }
//        });


        ArrayList<Integer> postIds = new ArrayList<>();
        // get all postids from server
    }
}
