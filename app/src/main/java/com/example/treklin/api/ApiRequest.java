package com.example.treklin.api;

import com.example.treklin.model.ResponseModel;
import com.example.treklin.model.ResponseModelArticle;
import com.example.treklin.model.ResponseModelOfficer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseModel> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude
    );

    @FormUrlEncoded
    @POST("register")
    Call<ResponseModel> registerUser(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/getDetailArticle")
    Call<ResponseModelArticle> getDetailArticle(
            @Field("id") String id
    );

    @GET("user/getArticle")
    Call<ResponseModelArticle> getArticle();

    @GET("user/getOfficer")
    Call<ResponseModelOfficer> getOfficer();

    @FormUrlEncoded
    @POST("user/userComplaint")
    Call<ResponseModel> userComplaint(
            @Field("userid") String userid,
            @Field("officerid") String officerid,
            @Field("complaint") String complaint
    );
}
