package com.example.photo_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.UserService;
import com.example.photo_app.model.Message;
import com.example.photo_app.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button registry_button, back_button;
    private TextView username, password, address, fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registry_button = findViewById(R.id.register_button);
        back_button = findViewById(R.id.back_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        fullname = findViewById(R.id.fullname);
        final Context context = getApplicationContext(); // Lấy Context của ứng dụng
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", MODE_PRIVATE);
                String token = sharedPreferences.getString("accessToken", "");
                System.out.println(token);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        registry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(null, username.getText().toString(), password.getText().toString(),
                        fullname.getText().toString(), address.getText().toString());
                UserService.authService.checkRegister(user).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful()) {
                            Message message = response.body();
                            Toast.makeText(RegisterActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: Username is already taken!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Unable to call server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

