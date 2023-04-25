package com.example.photo_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.EditProfileActivity;
import com.example.photo_app.FollowedViewActivity;
import com.example.photo_app.FollowingViewActivity;
import com.example.photo_app.R;
import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    private User user;
    private Button btnEditProfile;
    private TextView tvFollowers, tvFollowing, tvName, tvAddress;
    private LinearLayout lnListFollowers, lnListFollowing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        tvName = view.findViewById(R.id.tvName);
        tvAddress = view.findViewById(R.id.tvAddress);
        lnListFollowers = view.findViewById(R.id.lnListFollowers);
        lnListFollowing = view.findViewById(R.id.lnListFollowing);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        Context context = getContext();

        UserService userService = ApiClient.createService(UserService.class, context);
        Call<List<User>> call = userService.getUsersByFollowing();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> following = response.body();
                    Log.d("TAG", "onResponse following: " + following.size());
                    if (following != null) tvFollowing.setText(String.valueOf(following.size()));
                    else tvFollowing.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        call = userService.getUsersByFollowed();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followers = response.body();
                    Log.d("TAG", "onResponse followers: " + followers.size());
                    if (followers != null) tvFollowers.setText(String.valueOf(followers.size()));
                    else tvFollowers.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        Call<User> callGetUserFromJWT = userService.getUserFromJWT();
        callGetUserFromJWT.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user != null) {
                    tvName.setText(user.getFullName());
                    tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        lnListFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FollowedViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        lnListFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FollowingViewActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();

        UserService userService = ApiClient.createService(UserService.class, context);
        Call<List<User>> call = userService.getUsersByFollowing();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> following = response.body();
                    Log.d("TAG", "onResponse following: " + following.size());
                    if (following != null) tvFollowing.setText(String.valueOf(following.size()));
                    else tvFollowing.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        call = userService.getUsersByFollowed();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> followers = response.body();
                    Log.d("TAG", "onResponse followers: " + followers.size());
                    if (followers != null) tvFollowers.setText(String.valueOf(followers.size()));
                    else tvFollowers.setText("0");
                } else Log.d("TAG", "onResponse: " + response.code() + " " + response.message());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });

        Call<User> callGetUserFromJWT = userService.getUserFromJWT();
        callGetUserFromJWT.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user != null) {
                    tvName.setText(user.getFullName());
                    tvAddress.setText(user.getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
