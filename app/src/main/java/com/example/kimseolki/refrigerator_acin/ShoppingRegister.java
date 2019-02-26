package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.example.kimseolki.refrigerator_acin.model.Shopping;
import com.example.kimseolki.refrigerator_acin.service.PostShopping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by kimseolki on 2017-05-02.
 */

public class ShoppingRegister  extends AppCompatActivity {

    Spinner spipnner;
    EditText etName;
    EditText etMemo;
    GridView GvExFood;

    Integer shopping_id;
    String shopping_type;
    String shopping_name;
    String shopping_memo;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        spipnner = (Spinner) findViewById(R.id.cbFoodType);
        etName = (EditText) findViewById(R.id.etsname);
        etMemo = (EditText) findViewById(R.id.etsmemo);

        GvExFood = (GridView) findViewById(R.id.gvExFood); //유통기한 임박 식재료 리스트
        ArrayList<Food_location> food_locations = new ArrayList<Food_location>();
        food_locations = FoodInfo.getInstance().getFood_location();
        final ArrayList<Food_location> food_exdates = new ArrayList<Food_location>();
        for(int i=0; i< food_locations.size(); i++){
            if(food_locations.get(i).getD_day() >= -3){
                Food_location Exname = new Food_location(food_locations.get(i).getFood_Name(), food_locations.get(i).getD_day());
                food_exdates.add(Exname);
            }
        }

        Collections.sort(food_exdates, new Comparator<Food_location>() {
            @Override
            public int compare(Food_location o1, Food_location o2) {
                if(o1.getD_day() > o2.getD_day()) {
                    return 1;
                } else if (o1.getD_day() < o2.getD_day()) {
                    return -1;
                }else
                {
                    return 0;
                }
            }
        });
        Collections.reverse(food_exdates);
        ShoppingExpriationAdapter exdateListAdapter = new ShoppingExpriationAdapter(getApplicationContext(), food_exdates);
        exdateListAdapter.notifyDataSetChanged();
        GvExFood.setAdapter(exdateListAdapter);
        GvExFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etName.setText(food_exdates.get(i).getFood_Name());
            }
        });

        intent = getIntent();
        shopping_id = (Integer) intent.getSerializableExtra("shopping1");
        shopping_type = intent.getStringExtra("shopping2");
        shopping_name = intent.getStringExtra("shopping3");
        shopping_memo = intent.getStringExtra("shopping4");

        etName.setText(shopping_name);
        etMemo.setText(shopping_memo);

        ArrayAdapter sAdapter = ArrayAdapter.createFromResource(this, R.array.food_type, android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spipnner.setAdapter(sAdapter);

        if(shopping_type != null) {
            if (shopping_type.equals("육류")) {
                spipnner.setSelection(0);
            } else if (shopping_type.equals("어류")) {
                spipnner.setSelection(1);
            } else if (shopping_type.equals("과일류")) {
                spipnner.setSelection(2);
            } else if (shopping_type.equals("야채류")) {
                spipnner.setSelection(3);
            } else if (shopping_type.equals("유제품류")) {
                spipnner.setSelection(4);
            } else if (shopping_type.equals("음료류")) {
                spipnner.setSelection(5);
            } else if (shopping_type.equals("소스류")) {
                spipnner.setSelection(6);
            }
        }

        spipnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_showping, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_register:
                if(spipnner.getSelectedItem() != null && etName.getText() != null) {
                    Shopping pShopping = new Shopping(
                            spipnner.getSelectedItem().toString(),
                            etName.getText().toString(),
                            etMemo.getText().toString());
                    if(shopping_id != null){
                        modifyforShopping(pShopping);
                    }
                    if(shopping_id==null){
                        registerforShopping(pShopping);
                    }
                    finish();
                }else
                    Toast.makeText(getApplicationContext(), "구매할 식재료를 입력해주세요", Toast.LENGTH_SHORT).show();
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void modifyforShopping(Shopping pShopping) {
        intent = getIntent();
        shopping_id = (Integer) intent.getSerializableExtra("shopping1");

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostShopping client = retrofit.create(PostShopping.class);
        Call<Shopping> call= client.updateShopping(pShopping, shopping_id);     //DB에 이미 저장된 데이터를 새롭게 수정함

        call.enqueue(new Callback<Shopping>() {
            @Override
            public void onResponse(Call<Shopping> call, Response<Shopping> response) {

            }

            @Override
            public void onFailure(Call<Shopping> call, Throwable t) {

            }
        });
    }
    private void registerforShopping(Shopping pShopping) {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginInfo.getInstance().getIP_address())             //서버 주소입력
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostShopping client = retrofit.create(PostShopping.class);
        Call<Shopping> call= client.createShopping(pShopping);

        call.enqueue(new Callback<Shopping>() {
            @Override
            public void onResponse(Call<Shopping> call, Response<Shopping> response) {

            }

            @Override
            public void onFailure(Call<Shopping> call, Throwable t) {

            }
        });

    }
}
