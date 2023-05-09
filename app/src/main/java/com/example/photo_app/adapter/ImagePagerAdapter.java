package com.example.photo_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photo_app.ImageActivity;
import com.example.photo_app.R;
import com.example.photo_app.model.call.flickr.PhotoURLResponse;

import java.util.ArrayList;

// view pager adapter for showing list of images
public class ImagePagerAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private ArrayList<PhotoURLResponse> imageUrls;

    public ImagePagerAdapter(ArrayList<PhotoURLResponse> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_img_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position).getUrl();
        Log.d("image url", imageUrl);
        Log.d("size", String.valueOf(imageUrls.size()));
        Glide.with(holder.itemView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_android)
                .error(R.drawable.ic_android)
                .into(holder.imageView);

        holder.imageItem.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ImageActivity.class);
            intent.putExtra("image", imageUrls.get(position));
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    RelativeLayout imageItem;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imgView);
        imageItem = itemView.findViewById(R.id.imgItem);
    }
}
