package com.example.kimseolki.refrigerator_acin.service;

import com.example.kimseolki.refrigerator_acin.model.Comment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kimseolki on 2017-05-22.
 */

public interface GetComment {
    @GET("Comment/")
    Call<ArrayList<Comment>> all();

}
