package com.hoanhph29102.retrofitdemo.retrofit;

import com.hoanhph29102.retrofitdemo.SvRespon;
import com.hoanhph29102.retrofitdemo.SinhVien;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    String DOMAIN = "http://192.168.0.109:3002/";
    @FormUrlEncoded
    @POST("/")
    Call<SvRespon> insert(
            @Field("masv") String masv,
            @Field("tensv") String tensv
    );

    @GET("/")
    Call<List<SinhVien>> getAllSV();

    @PUT("/{id}")
    Call<Void> updateSinhVien(@Path("id") String id,
                              @Body SinhVien sinhVien);

    @DELETE("{id}")
    Call<Void> deleteSinhVien(@Path("id") String id);
}
