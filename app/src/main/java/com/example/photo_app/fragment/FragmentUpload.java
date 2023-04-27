package com.example.photo_app.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.photo_app.R;
import com.example.photo_app.adapter.ImageListAdapter;
import com.example.photo_app.api.FlickrService;
import com.example.photo_app.api.GoClient;
import com.example.photo_app.model.call.flickr.PhotoIdResponse;
import com.example.photo_app.model.call.flickr.PhotoSourceResponse;
import com.example.photo_app.utils.Utils;

import java.io.File;
import java.lang.reflect.Array;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class FragmentUpload extends Fragment {

    private ArrayList<String> selectedImages = new ArrayList<>();
    private ArrayList<File> selectedFiles = new ArrayList<>();
    private WebView webView;
    private CookieManager cookieManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> selectedImages = new ArrayList<>();
        Button btnSelect = view.findViewById(R.id.btnSelectPicture);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        Button loginWithFlickr = view.findViewById(R.id.btnFlickrLogin);
        EditText edtCaption = view.findViewById(R.id.edtCaption);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery to select multiple image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT).putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), 1);
            }
        });
        webView = view.findViewById(R.id.webViewFlickrLogin);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);

        cookieManager = new CookieManager();
        FlickrService flickrService = GoClient.createService(FlickrService.class, getActivity(), cookieManager);

        String baseURL = GoClient.getBaseUrl();
        String callbackUrlContains  = "redirect-callback";
        final String[] userID = new String[1];
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url){
                if (url.contains(callbackUrlContains)) {
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


                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_id", userID[0]));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_username", query_pairs.get("username")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_user_fullname", query_pairs.get("fullname")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token", query_pairs.get("flickr_request_token")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_request_token_secret", query_pairs.get("flickr_request_token_secret")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_token", query_pairs.get("flickr_access_token")));
                        cookieManager.getCookieStore().add(URI.create(baseURL), new HttpCookie("flickr_access_secret", query_pairs.get("flickr_access_secret")));

                        edtCaption.setText(userID[0]);
                        ViewGroup parent =(ViewGroup) webView.getParent();
                        parent.removeView(webView);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Return false to let the WebView load the URL normally
                super.onPageFinished(view, url);
            }
        });



        loginWithFlickr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                String url = baseURL+"flickr/auth";
                webView.loadUrl(url);
                loginWithFlickr.setVisibility(View.GONE);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload images to server
                if(selectedFiles.size()>0) {
//                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    List<MultipartBody.Part> fileParts = new ArrayList<>();
                    for(File file: selectedFiles){
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//                        requestBodyBuilder.addFormDataPart("files", file.getName(),requestBody);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                        fileParts.add(filePart);
                    }
//                    RequestBody requestBody = requestBodyBuilder.build();
                    Call<PhotoIdResponse> call = flickrService.uploadImages(fileParts);
                    call.enqueue(new Callback<PhotoIdResponse>() {
                        @Override
                        public void onResponse(Call<PhotoIdResponse> call, Response<PhotoIdResponse> response) {
                            System.out.println("SUCCESS with size --------- " + response.body().getResponse().size());
                            System.out.println(response.body().getResponse().get(0).getId());
                        }

                        @Override
                        public void onFailure(Call<PhotoIdResponse> call, Throwable t) {
                            System.out.println("FAILED");
                        }
                    });
                    selectedFiles.clear();
                    selectedImages.clear();


                    // Example call for single url : receive PhotoSourceResponse
                    String photoId ="";
                    Call<PhotoSourceResponse> call1 = flickrService.getImageUrl(photoId);
                    call1.enqueue(new Callback<PhotoSourceResponse>() {
                        @Override
                        public void onResponse(Call<PhotoSourceResponse> call, Response<PhotoSourceResponse> response) {
                            // url_o (Label original)
                            // url_q (Label large square url)
                            // url_t (thumbnail url)
                            // url_s, (label square)
                            // url_m, (Label small)
                            // url_n, (Label small320)
                            // url_z, (Label medium 640)
                            // url_c, (Label medium 800)
                            // url_l (Label: large square)
                            // h,k

                            //get source: direct link to png
                            //get url: link to size selection
                            String url = response.body().getPhotoSources().get(0).getSource();

                            // simple loading
//                            ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
//                            Glide.with(this).load("https://goo.gl/gEgYUd").into(imageView);

                        }

                        @Override
                        public void onFailure(Call<PhotoSourceResponse> call, Throwable t) {

                        }
                    });


                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {

                selectedImages.clear();
                // get list of selected images
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        selectedImages.add(data.getClipData().getItemAt(i).getUri().toString());
                        Uri uri = data.getClipData().getItemAt(i).getUri();
                        if(uri != null) {
                            File file = Utils.getFileFromUri(uri, getActivity());
                            if(file.exists()) {
                                selectedFiles.add(file);
                            }
                        }
                    }
                } else if (data.getData() != null) {
                    selectedImages.add(data.getData().toString());
                }
                ListView imgList = getView().findViewById(R.id.imgList);
                ImageListAdapter adapter = new ImageListAdapter(getContext(), selectedImages);
                imgList.setAdapter(adapter);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
