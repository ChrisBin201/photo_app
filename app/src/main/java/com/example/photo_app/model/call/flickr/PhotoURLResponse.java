package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

public class PhotoURLResponse {
    @SerializedName("url")
    private String url;

    public PhotoURLResponse(){}

    public PhotoURLResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
