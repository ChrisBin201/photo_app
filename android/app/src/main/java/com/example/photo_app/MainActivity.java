package com.example.photo_app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;


import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;

import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;


public class MainActivity extends AppCompatActivity {
    private boolean mIsInBackground = true;
    private WebView webView;
    private TextView txtText;
    private CookieManager cookieManager;
    OkHttpClient client;

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btnRedirect = findViewById(R.id.btnRedirect);
        Button btnUploadImg = findViewById(R.id.btnUploadImg);
        Button btnTest = findViewById(R.id.btnTest);

        txtText = findViewById(R.id.txtText);
        cookieManager = new CookieManager();

        final String[] userID = new String[1];
        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        String callbackUrl = "redirect-callback";
        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//            }
            @Override
            public void onPageFinished(WebView view, String url){
                if (url.contains(callbackUrl)) {
                    URL directUrl = null;
                    try {
                        directUrl = new URL(url);

                    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
                    String query = directUrl.getQuery();
                    String[] pairs = query.split("&");
                    for (String pair : pairs) {
                        String[] arr = pair.split("=");
                        query_pairs.put(arr[0], arr[1]);
                    }

                    userID[0]=query_pairs.get("user_id");
                    webView.setVisibility(View.GONE);


                        txtText.setText(userID[0]);
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_user_id", userID[0]));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_user_username", query_pairs.get("username")));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_user_fullname", query_pairs.get("fullname")));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_request_token", query_pairs.get("flickr_request_token")));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_request_token_secret", query_pairs.get("flickr_request_token_secret")));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_access_token", query_pairs.get("flickr_access_token")));
                        cookieManager.getCookieStore().add(URI.create("http://10.0.2.2:8900"), new HttpCookie("flickr_access_secret", query_pairs.get("flickr_access_secret")));


                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Return false to let the WebView load the URL normally
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());



        btnRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8900/flickr/auth";
                webView.loadUrl(url);
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8900";
                List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();

                System.out.println("---------------2-2------------------"+cookies);

                Request req = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    client.newCall(req).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // Handle the error
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            // Handle the response

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 1);

//                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                photoPickerIntent.setType("image/*");
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mIsInBackground) {
            System.out.println("----------back");
            Toast.makeText(this, "Return back from browser hehe", Toast.LENGTH_SHORT).show();
        }
        mIsInBackground = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isApplicationSentToBackground(this)) {
            // The app is going into the background, set the flag
            System.out.println("----------pause");
            mIsInBackground = true;
        }
    }

    /**
     * Helper method to check if the app is in the background
     */
    private boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
            return true;
        }
        return false;
    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {
                ClipData clipData = data.getClipData();
                ArrayList<Uri> uris = new ArrayList<>();

                if(clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); ++i) {
                        Uri uri = clipData.getItemAt(i).getUri();
                        if (uri != null) {
                            uris.add(uri);
                        }
                    }
                    uploadImage(uris, client);

                }
                else {
                    Uri uri = data.getData();
                    if (uri != null) {
                        // Upload the file to the server
                        uris.add(uri);
                        uploadImage(uris, client);
                    }
                }
            }
        }
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
    private File getFileFromUri(Uri uri) {
        String fileName = getFileName(uri);
        File tempFile = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            tempFile = File.createTempFile("temp", fileName, getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(tempFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    private void uploadImage(ArrayList<Uri> uris, OkHttpClient client) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(Uri uri : uris) {
            File file = getFileFromUri(uri);
            if(file.exists()){
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                requestBodyBuilder.addFormDataPart("files", file.getName(), requestBody);
            }
        }
        RequestBody requestBody = requestBodyBuilder.build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8900/flickr/upload_img")
                .post(requestBody)
                .build();

        // Send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Handle the error
                Log.e("Upload Image", "Error uploading image", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Handle the response
                Log.d("Upload Image", "Response: " + response.body().string());
            }
        });
    }
}