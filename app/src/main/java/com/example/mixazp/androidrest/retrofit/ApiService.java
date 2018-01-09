package com.example.mixazp.androidrest.retrofit;

import com.example.mixazp.androidrest.model.FinalProduct;
import com.example.mixazp.androidrest.model.Product;
import com.example.mixazp.androidrest.model.RegistrationRequest;
import com.example.mixazp.androidrest.model.RegistrationResponse;
import com.example.mixazp.androidrest.model.ReviewRequest;
import com.example.mixazp.androidrest.model.ReviewResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/products")
    Call<ArrayList<Product>> getMyJSON(); // список товаров

    @POST("/api/register/")
    Call<RegistrationResponse> registerUser(@Body RegistrationRequest registrationBody); // регистрация

    @POST("/api/login/")
    Call<RegistrationResponse> autorizationUser(@Body RegistrationRequest registrationBody); // авторизация

    @GET("api/reviews/{id}")
    Call<ArrayList<FinalProduct>> getMyProduct(@Path("id") int id); // возвращает продукт по id

    @POST("/api/reviews/{review_id}")
    Call<ReviewResponse> reviewRequest(@Body ReviewRequest review, @Path("review_id") int review_id); // отзыв о продукте
}