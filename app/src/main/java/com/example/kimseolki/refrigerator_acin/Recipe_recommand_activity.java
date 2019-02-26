package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Recipe;
import com.example.kimseolki.refrigerator_acin.service.GetRecipe;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 6201P-03 on 2017-05-31.
 */

public class Recipe_recommand_activity extends AppCompatActivity {
    private GetRecipe getRecipe;
    ListView lvRecipe;
    ArrayList<Recipe> recipe;
    ArrayList<Recipe> searchRecipe;
    private RecipeListAdapter recipe_adapter;
    String s;

    private static final String TAG ="Recipe" ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommandrecipe_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getRecipe = retrofit.create(GetRecipe.class);
        lvRecipe = (ListView) findViewById(R.id.lvRecipe);
        recipe = new ArrayList<Recipe>();
        searchRecipe = new ArrayList<Recipe>();

        Intent rIntent = getIntent();
        s = rIntent.getStringExtra("food").toString();
        loadRecipe();
        lvRecipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String recipeURL = searchRecipe.get(position).getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri u = Uri.parse(recipeURL);
                intent.setData(u);
                startActivity(intent);
            }
        });

    }

    private void loadRecipe() {
        Call<ArrayList<Recipe>> call = getRecipe.all();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipe = response.body();
                searchRecipe.clear();
                searchRecipe = new ArrayList<Recipe>();
                for(int i=0; i < recipe.size() ;i++) {
                    try{
                        if (recipe.get(i).getTitle().matches(".*"+s+".*")) {
                            Recipe sRecipe = new Recipe(recipe.get(i).getImage(), recipe.get(i).getLink(), recipe.get(i).getTitle());
                            searchRecipe.add(sRecipe);
                        }
                    }
                    catch (PatternSyntaxException e) {
                        System.err.println(e);
                        System.exit(1);
                    }
                }
                recipe_adapter = new RecipeListAdapter(getApplicationContext(), searchRecipe);
                lvRecipe.setAdapter(recipe_adapter);


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });
    }
}
