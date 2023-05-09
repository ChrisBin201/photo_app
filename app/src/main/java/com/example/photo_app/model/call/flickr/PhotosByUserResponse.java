package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosByUserResponse {
    @SerializedName("photos")
    private List<photoByUserResponse> photos;

    public PhotosByUserResponse(List<photoByUserResponse> photos) {
        this.photos = photos;
    }

    public List<photoByUserResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<photoByUserResponse> photos) {
        this.photos = photos;
    }

    public class photoByUserResponse {
        @SerializedName("url")
        private String url;

        public photoByUserResponse(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
