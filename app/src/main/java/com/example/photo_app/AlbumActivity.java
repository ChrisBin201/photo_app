package com.example.photo_app;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.photo_app.adapter.RecycleViewAdapterAlbum;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.fragment.FragmentUpload;
import com.example.photo_app.model.call.flickr.PhotoSourceResponse;
import com.example.photo_app.model.call.flickr.PhotosetsResponse;
import com.example.photo_app.model.call.flickr.PhotosetsResponse.PhotosetResponse;

import java.net.CookieManager;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


public class AlbumActivity extends AppCompatActivity {
    private ArrayList<PhotosetResponse> photosetsResponse;
    private RecycleViewAdapterAlbum recycleViewAdapterAlbum;
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        recycleViewAdapterAlbum = new RecycleViewAdapterAlbum();
        recycleView = findViewById(R.id.album_gridView);
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlbumActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        photosetsResponse = (ArrayList<PhotosetResponse>) bundle.get("photosets_response");
        recycleViewAdapterAlbum.setList(photosetsResponse);
        recycleView.setAdapter(recycleViewAdapterAlbum);
        GridLayoutManager manager = new GridLayoutManager(getApplicationContext(), 3, RecyclerView.VERTICAL, false);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(recycleViewAdapterAlbum);
    }

    public String getImageUrlByImageId(String id) {
        String imgUrl = "";
        CookieManager cookieManager = FragmentUpload.getCookieManager();
        FlickrService flickrService = GoClient.createService(FlickrService.class, getApplicationContext(), cookieManager);
        Call<PhotoSourceResponse> call = flickrService.getImageUrl(id);
        call.enqueue(new Callback<PhotoSourceResponse>() {
            @Override
            public void onResponse(Call<PhotoSourceResponse> call, Response<PhotoSourceResponse> response) {
                System.out.println("SUCCESS");

            }

            @Override
            public void onFailure(Call<PhotoSourceResponse> call, Throwable t) {
                System.out.println("FAILED getting url from url");
            }
        });
        return imgUrl;
    }
}