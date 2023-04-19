package com.example.photo_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.UserService;
import com.example.photo_app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvFollowers, tvFollowing;
    private List<User> followers, following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snippet_top_profile);

        tvFollowers = findViewById(R.id.tvFollowers);
        tvFollowing = findViewById(R.id.tvFollowing);

        UserService.authService.getUserFromJWT().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    UserService.authService.getUsersByFollowed(user.getId()).enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            if (response.isSuccessful()) {
                                following = response.body();
                                tvFollowing.setText(String.valueOf(following.size()));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(HomeActivity.this, "Can't get data followed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    UserService.authService.getUsersByFollowing(user.getId()).enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            if (response.isSuccessful()) {
                                followers = response.body();
                                tvFollowers.setText(String.valueOf(followers.size()));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            Toast.makeText(HomeActivity.this, "Can't get data following", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Can't get user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
