package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Info;
import com.example.kimseolki.refrigerator_acin.service.GetFoodInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HealthInfoFragment extends Fragment {
    public static final String TAG = "hazard";
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

    private GetFoodInfo getFoodInfo;
    ListView listView;
    ArrayList<String> infoList;
    ArrayAdapter<String> info_adapter;
    ArrayList<Integer> infoId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_hazardfood_main,container,false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoodInfo = retrofit.create(GetFoodInfo.class);
        listView = (ListView) rootview.findViewById(R.id.hazardfoodl);
        infoList = new ArrayList<String>();
        info_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, infoList);
        listView.setAdapter(info_adapter);
        infoId = new ArrayList<Integer>();
        loadTodos();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HealthInfoFragment_post.class);
                int nInfoid = infoId.get(position);
                intent.putExtra("Id", nInfoid);
                startActivity(intent);
            }
        });

        return rootview;
    }

    private void loadTodos() {
        Call<List<Info>> call = getFoodInfo.all();
        call.enqueue(new Callback<List<Info>>() {
            @Override
            public void onResponse(Call<List<Info>> call, Response<List<Info>> response) {
                List<Info> info = response.body();
                displayResult(info);
            }
            @Override
            public void onFailure(Call<List<Info>> call, Throwable t) {
            }
        });
    }

    private void displayResult(List<Info> info) {
        if (info!= null) {
            infoList.clear();
            for(int i=0; i<info.size(); i++) {
                String name = info.get(i).getTitle() +  "\n";
                infoList.add(name);
                infoId.add(info.get(i).getId());
                info_adapter.notifyDataSetChanged();
            }
        } else {

        }
    }
}
