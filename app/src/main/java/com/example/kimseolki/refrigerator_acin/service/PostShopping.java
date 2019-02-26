package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Shopping;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by kimseolki on 2017-05-03.
 */

public interface PostShopping {
    @POST("Shopping/")
    Call<Shopping> createShopping(@Body Shopping shopping);
    @PUT("Shopping/{id}/")
    Call<Shopping> updateShopping(@Body Shopping shopping, @Path("id") int id);
}
