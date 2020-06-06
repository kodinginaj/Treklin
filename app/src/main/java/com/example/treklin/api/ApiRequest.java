package com.example.treklin.api;

import com.example.treklin.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequest {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseModel> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<ResponseModel> registerUser(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("password") String password
    );
}
