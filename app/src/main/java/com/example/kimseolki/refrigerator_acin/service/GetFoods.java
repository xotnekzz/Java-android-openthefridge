package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Food;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * Created by kimseolki on 2017-05-02.
 */

public interface GetFoods {
    @GET("Food/")
    Call<ArrayList<Food>> all();

    @GET("Food/{id}/")
    Call<Food> select(@Path("food_number") int id);

    @DELETE("Food/{id}/")
    Call<Void> removeFood(@Path( "id")  Integer id);
}

