package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.PFood;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-03.
 */

public interface PostFoods {
    @POST("Food/")
    Call<PFood> createAccount(@Body PFood pfood);
    @PUT("Food/{id}/")
    Call<PFood> updateAccount(@Body PFood pfood, @Path("id") int id);
}
