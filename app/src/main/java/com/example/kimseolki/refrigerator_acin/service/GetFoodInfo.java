package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-12.
 */

public interface GetFoodInfo {
    @GET("Info/")
    Call<List<Info>>all();

    @GET("Info/{id}/")
    Call<Info> select(@Path("id") int id);
}
