package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Memo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-24.
 */

public interface PostMemo {
    @POST("Memo/")
    Call<Memo> createAccount(@Body Memo memo);
    @PUT("Memo/{id}/")
    Call<Memo> updateMemo(@Body Memo memo, @Path("id") int id);
}
