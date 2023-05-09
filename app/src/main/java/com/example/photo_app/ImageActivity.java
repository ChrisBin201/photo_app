package com.example.photo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.photo_app.fragment.ratingComment.FragmentViewComments;
import com.example.photo_app.fragment.ratingComment.FragmentViewRatings;
import com.example.photo_app.model.call.flickr.PhotoURLResponse;

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = "ImageActivity";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ImageView btnBack, image;
    private Button btnRating, btnComment;
    private FrameLayout mFrameLayout;
    private RelativeLayout mRelativeLayout;

    private void add(Fragment fg, String tag, String name){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container,fg,tag);
        transaction.addToBackStack(name);
        transaction.commit();
    }

    public void hideLayout(){
        Log.d(TAG, "hideLayout: hiding layout");
        mRelativeLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }


    public void showLayout(){
        Log.d(TAG, "hideLayout: showing layout");
        mRelativeLayout.setVisibility(View.VISIBLE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        btnBack = findViewById(R.id.backArrow);
        image = findViewById(R.id.image);
        btnComment = findViewById(R.id.btnComment);
        btnRating = findViewById(R.id.btnRating);
        mFrameLayout = (FrameLayout) findViewById(R.id.container);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rlMain);
        manager = getSupportFragmentManager();


        PhotoURLResponse photo = (PhotoURLResponse)getIntent().getSerializableExtra("image");
        Glide.with(image)
                .load(photo.getUrl())
                .placeholder(R.drawable.ic_android)
                .error(R.drawable.ic_android)
                .into(image);

        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnComment.setOnClickListener(view -> {
            FragmentViewComments fragment = new FragmentViewComments();
            Bundle args = new Bundle();
//            args.putParcelable(getString(R.string.photo), photo);
//            args.putString(getString(R.string.home_activity), getString(R.string.home_activity));
            args.putString("photo_id", photo.getId());
            fragment.setArguments(args);
            add(fragment, "comment", "comment");
            hideLayout();
        });

        btnRating.setOnClickListener(view -> {
            FragmentViewRatings fragment = new FragmentViewRatings();
            Bundle args = new Bundle();
//            args.putParcelable(getString(R.string.photo), photo);
//            args.putString(getString(R.string.home_activity), getString(R.string.home_activity));
            args.putString("photo_id", photo.getId());
            fragment.setArguments(args);
            add(fragment, "rating", "rating");
            hideLayout();
        });



    }
}