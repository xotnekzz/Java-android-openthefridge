package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hey on 2017-05-16.
 */

public interface GetRecipe {
    @GET("Recipe/")
    Call<ArrayList<Recipe>> all();
}
