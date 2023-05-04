package com.example.photo_app;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        GridView albumGridView = findViewById(R.id.album_gridView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        photosetsResponse = (ArrayList<PhotosetResponse>) bundle.get("photosets_response");

        AlbumAdapter albumAdapter = new AlbumAdapter();
        albumGridView.setAdapter(albumAdapter);
    }

    public class AlbumAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return photosetsResponse.size();
        }

        @Override
        public Object getItem(int position) {
            return photosetsResponse.get(position);
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item_album,null);

            ImageView img = view.findViewById(R.id.img_primary_album);
            TextView title = view.findViewById(R.id.txtTitleAlbum);
            String url = "";
            title.setText(photosetsResponse.get(position).getTitle());
//            Glide.with(holder.itemView)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.ic_android)
//                    .error(R.drawable.ic_android)
//                    .into(holder.imageView);
            return view;
        }
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