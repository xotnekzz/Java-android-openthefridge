package com.example.kimseolki.refrigerator_acin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class ExpriationFragment extends Fragment {

    private GetFoods getFoods;
    ListView lvFoods;
    ArrayList<Food_location> food_location;
    ExdateListAdapter Exdate_adapter;
    int day;

    @Override
    public void onStart() {
        loadExdate();
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_expriation,container,false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoods = retrofit.create(GetFoods.class);
        lvFoods = (ListView) rootview.findViewById(R.id.lvExpriation);

        food_location= new ArrayList<Food_location>();
        loadExdate();
        return rootview;
    }

    private void loadExdate() {
        food_location = FoodInfo.getInstance().getFood_location();
        Collections.sort(food_location, new Comparator<Food_location>() {
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
        Collections.reverse(food_location);
        Exdate_adapter = new ExdateListAdapter(getActivity(), food_location);
        lvFoods.setAdapter(Exdate_adapter);
        Exdate_adapter.notifyDataSetChanged();
    }

 /*   private void loadTodos() {
        Call<ArrayList<Food>> call = getFoods.all();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                List<Food> foods = response.body();
                displayResult(foods);
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }
    private void displayResult(List<Food> foods) {
        ArrayList<Food_location> food_location = new ArrayList<Food_location>();
        if (foods != null) {
            food_list.clear();
            for(int i=0; i<foods.size(); i++) {  *//* 유통기한 날짜 설정 *//*
                 *//* 유통기한 날짜 설정 *//*
                String exdate = foods.get(i).getFood_exdate().toString();
                String[] exdate_array;                       //split()을 쓰기위해 스트링배열 선언
                exdate_array = exdate.split(". "); // '.'으로 연/월/일 구분
                if(exdate_array != null) {
                    eYear = Integer.parseInt(exdate_array[0]);
                    eMonth = Integer.parseInt(exdate_array[1]);
                    eDay = Integer.parseInt(exdate_array[2]);
                }
                Log.d("일", String.valueOf(eDay));
                GregorianCalendar calendar_exdate = new GregorianCalendar();
                if(eMonth == 6){
                    eDay = eDay +1;
                    calendar_exdate.set(Calendar.YEAR, eYear);
                    calendar_exdate.set(Calendar.MONTH, eMonth);
                    calendar_exdate.set(Calendar.DATE, eDay);
                } else {
                    calendar_exdate.set(Calendar.YEAR, eYear);
                    calendar_exdate.set(Calendar.MONTH, eMonth);
                    calendar_exdate.set(Calendar.DATE, eDay);
                }

                          *//* 현재 날짜 설정 *//*
                GregorianCalendar calendar = new GregorianCalendar();
                year = calendar.get(Calendar.YEAR);
                Log.d("현재날짜", String.valueOf(year));
                month = calendar.get(Calendar.MONTH) + 1;
                Log.d("현재날짜", String.valueOf(month));
                day= calendar.get(Calendar.DAY_OF_MONTH);
                Log.d("현재날짜", String.valueOf(day));

                calendar.set(year, month, day);

                        *//* D_day 계산 *//*
                long currentTime = calendar.getTimeInMillis() / (86400000);
                Log.d("현재날짜", String.valueOf(currentTime));
                long expriation = calendar_exdate.getTimeInMillis() / (86400000);
                Log.d("유통기한", String.valueOf(expriation));
                int d_day = (int) (currentTime - expriation);
                Log.d("디데이", String.valueOf(d_day));

                Food_location food_locations = new Food_location(foods.get(i).getFood_name(), foods.get(i).getFood_exdate(), foods.get(i).getFood_Image(), d_day);
                food_location.add(food_locations);
            }

            Collections.sort(food_location, new Comparator<Food_location>() {
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
                    Collections.reverse(food_location);
            Exdate_adapter = new ExdateListAdapter(getActivity(), food_location);
            lvFoods.setAdapter(Exdate_adapter);
        }
    }

    public ArrayList<String> GetFood_exdate(ArrayList<Food_location> food_location) {
        ArrayList<String> s = new ArrayList<>();
        for(int i=0; i < food_location.size(); i++){
            if(food_location.get(i).getD_day() <= 3){
                s.add(food_location.get(i).getFood_Name());
            }
        }
        return s;
    }*/
}
