package com.example.photo_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.EditProfileActivity;
import com.example.photo_app.R;

public class FragmentProfile extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnEditProfile;
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
//
//        UserService.authService.getUsersByFollowing().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful()) {
//                    List<User> following = response.body();
//                    Log.i("TAG", "onResponse: " + following.size());
//                    Toast.makeText(getContext(), following.size(), Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(getContext(), response.code() + " "
//                        + response.message(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(getContext(), "Unable to call server", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        UserService.authService.getUsersByFollowed().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful()) {
//                    List<User> follower = response.body();
//                    Log.i("TAG", "onResponse: " + follower.size());
//                    Toast.makeText(getContext(), follower.size(), Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(getContext(), response.code() + " "
//                        + response.message(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(getContext(), "Unable to call server", Toast.LENGTH_SHORT).show();
//            }
//        });

//        UserService userService = ApiClient.createService(UserService.class, context);
//        Call<List<User>> call = userService.getUsersByFollowing();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful()) {
//                    List<User> following = response.body();
//                    Log.i("TAG", "onResponse: " + following.size());
//                    Toast.makeText(context, following.size(), Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(context, response.code() + " "
//                        + response.message(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        UserService.authService.getUsersByFollowing().enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful()) {
//                    List<User> followers = response.body();
//                    Log.i("TAG", "onResponse: " + followers.size());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                Toast.makeText(context, "Unable to call server", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
