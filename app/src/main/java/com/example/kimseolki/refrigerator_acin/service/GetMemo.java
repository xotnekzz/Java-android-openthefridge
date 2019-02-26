package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Memo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-20.
 */

public interface GetMemo {
    @GET("Memo/")
    Call<ArrayList<Memo>> all();

    @GET("Memo/{id}/")
    Call<Memo> select(@Path("id") int id);

    @DELETE("Memo/{id}/")
    Call<Void> removeMemo(@Path( "id")  Integer id);
}
