package com.example.photo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.UserService;
import com.example.photo_app.model.LoginRequest;
import com.example.photo_app.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity  {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView buttonRegister;
//    private CallbackManager callbackManager;
//    private LoginButton loginButtonFB;
    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.link_signup);
//        loginButtonFB = findViewById(R.id.login_button);
//        callbackManager = CallbackManager.Factory.create();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                LoginResponse loginResponse = new LoginResponse(username, password);
                UserService.authService.checkLogin(loginResponse).enqueue(new Callback<LoginRequest>() {
                    @Override
                    public void onResponse(Call<LoginRequest> call, retrofit2.Response<LoginRequest> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            LoginRequest loginRequest = response.body();

                            // chuyển sang màn hình home
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);

                            //  Lưu giá trị token vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("accessToken", loginRequest.getAccessToken());
                            editor.apply();
                            System.out.println(loginRequest.getAccessToken());
                        } else {
                            Toast.makeText(MainActivity.this, "sai thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginRequest> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "không gọi được đến server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

//        loginButtonFB.setPermissions("email", "public_profile");
//
//        // Callback registration
//        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // Lấy access token sử dụng LoginResult
//                AccessToken accessToken = loginResult.getAccessToken();
//                // Sử dụng access token để lấy thông tin người dùng
//                useLoginInformation(accessToken);
//                // Lấy thông tin người dùng
//                Profile profile = Profile.getCurrentProfile();
//                // Hiển thị thông tin người dùng
//                Toast.makeText(MainActivity.this, profile.getName(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                // App code
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void useLoginInformation(AccessToken accessToken) {
//        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                try {
//                    String email = object.getString("email");
//                    String name = object.getString("name");
//                    String id = object.getString("id");
//                    System.out.println(email);
//                    System.out.println(name);
//                    System.out.println(id);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Bundle bundle = new Bundle();
//        bundle.putString("fields", "email,name,id");
//        graphRequest.setParameters(bundle);
//        graphRequest.executeAsync();
//    }
    }
}


