package com.hoanhph29102.retrofitdemo.retrofit;

import com.hoanhph29102.retrofitdemo.SvRespon;
import com.hoanhph29102.retrofitdemo.TestModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/")
    Call<SvRespon> insert(
            @Field("masv") String masv,
            @Field("tensv") String tensv
    );

    @GET("/")
    Call<List<TestModel>> getAllSV();
}
