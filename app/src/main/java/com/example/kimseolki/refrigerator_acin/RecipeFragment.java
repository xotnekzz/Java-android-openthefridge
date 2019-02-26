package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.kimseolki.refrigerator_acin.model.Recipe;
import com.example.kimseolki.refrigerator_acin.service.GetRecipe;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RecipeFragment extends Fragment {
    private GetRecipe getRecipe;
    ListView lvRecipe;
    ArrayList<Recipe> recipe;
    ArrayList<Recipe> searchRecipe;
    private RecipeListAdapter recipe_adapter;
    SearchView searchView;
    private static final String TAG ="Recipe" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_recipe_main,container,false);

        searchView = (SearchView) rootview.findViewById(R.id.svFood);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getRecipe = retrofit.create(GetRecipe.class);
        lvRecipe = (ListView) rootview.findViewById(R.id.lvRecipe);
        recipe = new ArrayList<Recipe>();
        searchRecipe = new ArrayList<Recipe>();

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

        return rootview;
    }
    private void loadRecipe() {
        Call<ArrayList<Recipe>> call = getRecipe.all();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipe = response.body();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        searchRecipe.clear();
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
                        recipe_adapter = new RecipeListAdapter(getActivity(), searchRecipe);
                        lvRecipe.setAdapter(recipe_adapter);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {

            }
        });
    }
}
