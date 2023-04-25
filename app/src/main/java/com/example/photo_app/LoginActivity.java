package com.example.photo_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photo_app.api.ApiClient;
import com.example.photo_app.api.UserService;
import com.example.photo_app.model.call.LoginRequest;
import com.example.photo_app.model.call.LoginResponse;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView buttonRegister;
    private CallbackManager callbackManager;
    private LoginButton loginButtonFB;


    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.link_signup);
        loginButtonFB = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                LoginRequest loginRequest = new LoginRequest(username, password);

                Context context = getApplicationContext();
//                String token = context.getSharedPreferences("dataLogin", MODE_PRIVATE).getString("accessToken", "");
//                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

//                if (context == null) {
//                    Toast.makeText(MainActivity.this, "Context is null", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Context is not null", Toast.LENGTH_SHORT).show();
//                }

                UserService authService = ApiClient.createService(UserService.class, context);

                Call<LoginResponse> call = authService.checkLogin(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            LoginResponse loginResponse = response.body();

                            //  Lưu giá trị token vào SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("dataLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("token", loginResponse.getAccessToken());
                            editor.apply();


                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Unable to call server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButtonFB.setPermissions("email", "public_profile");

        // Callback registration
        loginButtonFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Lấy access token sử dụng LoginResult
                AccessToken accessToken = loginResult.getAccessToken();
                // Sử dụng access token để lấy thông tin người dùng
                useLoginInformation(accessToken);
//                // Lấy thông tin người dùng
//                Profile profile = Profile.getCurrentProfile();
                // Hiển thị thông tin người dùng
//                AccessToken accessToken1 = AccessToken.getCurrentAccessToken();
//                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//                Toast.makeText(MainActivity.this, isLoggedIn? accessToken.toString():"false", Toast.LENGTH_SHORT).show();
                Map<String,String> tokenMap =  new HashMap<>();
                tokenMap.put("accessToken", accessToken.getToken());
                UserService AuthService = ApiClient.createService(UserService.class, getApplicationContext());
                AuthService.authService.checkLoginFB(tokenMap).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            LoginResponse loginResponse = response.body();

                            //  Lưu giá trị token vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token", loginResponse.getAccessToken());
                            editor.apply();
                            System.out.println(loginResponse.getAccessToken());

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
//                            Toast.makeText(MainActivity.this, "sai thông tin", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, response.code() + " "+ response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "không gọi được đến server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void useLoginInformation(AccessToken accessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String email = object.getString("email");
                    String name = object.getString("name");
                    String id = object.getString("id");
                    System.out.println(accessToken.getToken());
                    System.out.println(email);
                    System.out.println(name);
                    System.out.println(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "email,name,id");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }
}
