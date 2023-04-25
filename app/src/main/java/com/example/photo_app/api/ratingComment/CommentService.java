package com.example.photo_app.api.ratingComment;


import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentService {
//    Gson gson = new GsonBuilder()
//            .setLenient()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .create();
////
//    AuthService authService = new Retrofit.Builder()
//            .baseUrl("http://127.0.0.1:9000/api/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(AuthService.class);

//    CommentService COMMENT_SERVICE = ApiConfig.retrofit.create(CommentService.class);


    @POST("comment")
    Call<MessageResponse<Comment>> create(@Body CommentDTO comment);

    @GET("comment/post/{id}")
    Call<MessageResponse<List<Comment>>> getAllByPost(@Path("id") String id);
    @GET("comment/photo/{id}")
    Call<MessageResponse<List<Comment>>> getAllByPhoto(@Path("id") String id);

    @PUT("comment")
    Call<MessageResponse<Comment>> update(@Body CommentDTO comment);

    @DELETE("comment/{id}")
    Call<MessageResponse<String>> deleteById(@Path("id") String id);

}
