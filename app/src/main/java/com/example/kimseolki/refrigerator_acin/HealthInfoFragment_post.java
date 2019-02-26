package com.example.kimseolki.refrigerator_acin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimseolki.refrigerator_acin.model.Info;
import com.example.kimseolki.refrigerator_acin.service.GetFoodInfo;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-12.
 */

public class HealthInfoFragment_post extends AppCompatActivity {

    private static final String TAG = "sub" ;
    private GetFoodInfo getFoodInfo;
    TextView tvtitle;
    TextView tvbody;
    ImageView ivInfo;
    Context context;
    int id;
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hazardfood_post);
        tvtitle = (TextView) findViewById(R.id.tvTitle);
        tvbody = (TextView) findViewById(R.id.tvBody);
        ivInfo = (ImageView) findViewById(R.id.ivInfo);
        id = getIntent().getExtras().getInt("Id");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoodInfo = retrofit.create(GetFoodInfo.class);
        loadInfo();
    }

    private void loadInfo() {
        Call<Info> call = getFoodInfo.select(id);
        call.enqueue(new Callback<Info>() {

            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Info f = response.body();
                if(f.getTitle() != null || f.getPost() != null) {
                    String title = f.getTitle();
                    String post = f.getPost();
                    tvtitle.setText(title);
                    tvbody.setText(post);
                }
               if(f.getImage()!=null) {
                   Picasso.with(getApplicationContext()).load(f.getImage()).error(R.mipmap.ic_launcher).into(ivInfo);
               }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {

            }
        });

    }

}
