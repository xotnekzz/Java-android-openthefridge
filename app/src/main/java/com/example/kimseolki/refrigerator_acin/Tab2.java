package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kimseolki.refrigerator_acin.model.Food;
import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.example.kimseolki.refrigerator_acin.model.Food_modify;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class Tab2 extends Fragment {
    private static final String TAG ="Tap2" ;
    private GetFoods getFoods;
    GridView lvFoods;
    private ArrayList<Food> food_list;
    private FoodListAdapter food_adapter;
    ArrayList<Integer> fId = new ArrayList<Integer>();      //삭제의 food_number를 구분하기위한 ArrayList
    ArrayList<Food_location> foods_location;
    ArrayList<Food_modify> foods_modify;
    ArrayList<Food> foods;
    int year, month, day;
    int eYear, eMonth, eDay;

    @Override
    public void onStart() {
        /* Fragment 생성시 loadFoods() 호출 */
        Log.d(TAG, "onStart");
        super.onStart();
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadFoods();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        loadFoods();
        registerForContextMenu(lvFoods);
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        unregisterForContextMenu(lvFoods);
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        unregisterForContextMenu(lvFoods);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.tab2_freeze,container,false);

        /* Rerrofit을 사용하여 서버와 통신*/

        /* Retrofit 생성 */
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoods = retrofit.create(GetFoods.class);
        lvFoods = (GridView) rootview.findViewById(R.id.lvFood2);
        food_list = new ArrayList<Food>();

        registerForContextMenu(lvFoods);
        lvFoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent tIntent = new Intent(getContext(), RefrigeratorModify.class);
                Bundle bundle = new Bundle();
                Integer food_number = foods_modify.get(i).getFood_number();
                String food_type = foods_modify.get(i).getFood_type();
                String food_name = foods_modify.get(i).getFood_name();
                String food_purchase = foods_modify.get(i).getFood_purchase();
                String food_exdate = foods_modify.get(i).getFood_exdate();
                String food_location = foods_modify.get(i).getFood_location();
                String food_image = foods_modify.get(i).getFood_image();
                bundle.putSerializable("foods1", (Serializable) food_number);
                bundle.putSerializable("foods2", (Serializable) food_type);
                bundle.putSerializable("foods3", (Serializable) food_name);
                bundle.putSerializable("foods4", (Serializable) food_purchase);
                bundle.putSerializable("foods5", (Serializable) food_exdate);
                bundle.putSerializable("foods6", (Serializable) food_location);
                bundle.putSerializable("foods7", (Serializable) food_image);
                tIntent.putExtras(bundle);
                startActivity(tIntent); // 식재료 수정 액티비티(RefrigeratorModify) 전환
            }
        });

        return rootview;
    }

    /* 서버로부터 식재료 정보 불러오기 */
    private void loadFoods() {
        Call<ArrayList<Food>> call = getFoods.all();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                foods = response.body();
                foods_location = new ArrayList<Food_location>();
                foods_modify = new ArrayList<Food_modify>();

                /* 식재료 위치 구분*/
                for(int i=0; i < foods.size(); i++){
                    if(foods.get(i).getFood_location().equals("냉동")){

                        /* 유통기한 날짜 설정 */
                        String exdate = foods.get(i).getFood_exdate().toString();
                        String[] exdate_array;                       //split()을 쓰기위해 스트링배열 선언
                        exdate_array = exdate.split(". "); // '.'으로 연/월/일 구분
                        if(exdate_array != null) {
                            eYear = Integer.parseInt(exdate_array[0]);
                            eMonth = Integer.parseInt(exdate_array[1]);
                            eDay = Integer.parseInt(exdate_array[2]);
                        }
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


                         /* 현재 날짜 설정 */
                        GregorianCalendar calendar = new GregorianCalendar();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH) + 1;
                        day= calendar.get(Calendar.DAY_OF_MONTH);

                        calendar.set(year, month, day);

                        /* D_day 계산 */
                        long currentTime = calendar.getTimeInMillis() / (86400000);
                        long expriation = calendar_exdate.getTimeInMillis() / (86400000);
                        int d_day = (int) (currentTime - expriation)+1;

                        Food_location food_locations = new Food_location(foods.get(i).getFood_name(), foods.get(i).getFood_exdate(), foods.get(i).getFood_Image(), d_day,foods.get(i).getFood_type());
                        foods_location.add(food_locations);
                        /* 식재료 수정 코드 */
                        Food_modify food_modifys = new Food_modify(foods.get(i).getFood_number(), foods.get(i).getFood_type(),
                                foods.get(i).getFood_name(), foods.get(i).getFood_exdate(), foods.get(i).getFood_purchase(), foods.get(i).getFood_location(), foods.get(i).getFood_Image());
                        foods_modify.add(food_modifys);

                        /* 식재료 삭제*/
                        fId.add(foods.get(i).getFood_number());         //삭제에 필요한 food_number를 arrayList에 저장해줌
                    }

                }
                displayResult(foods_location);
            }
            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }
    /* 식재료 정보 리스트뷰화 */
    private void displayResult(ArrayList<Food_location> foods) {
        if (foods != null) {
            String details = "";
            food_list.clear();
            food_adapter = new FoodListAdapter(getActivity(), foods);
            food_adapter.notifyDataSetChanged();
            lvFoods.setAdapter(food_adapter);

        } else {

            // 에러 구문 삽입

        }
    }
    /* 수정/삭제 컨텍스트 메뉴 생성 */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onContextItemCreate");
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_lcilckmenu, menu);
    }
    /* 수정/삭제 컨텍스트 메뉴 동작 */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected");

        if (!getUserVisibleHint()) {
            Log.d(TAG, "onContextItemSelected:getUserVisibleHint:false");
            return super.onContextItemSelected(item);
        }

        AdapterView.AdapterContextMenuInfo cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mDelete: // 삭제
                cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                foods_location.remove(cmenuInfo.position);
                food_adapter.notifyDataSetChanged();
                Integer f = fId.get(cmenuInfo.position);       //Click한 Item에 position값을 이용하여 fId에 저장되어있는 Click한 Item의 food_number값을 저장
                removeFood(f);                                  //DB에 삭제하는 함수
                fId.remove(cmenuInfo.position);                 //DB에 삭제했지만 f 라는 arraylist에는 삭제되지 않았기때문에 삭제해줌.
                return true;
            default:
                return true;
        }
    }

    /* 식재료 삭제 */
    private void removeFood(Integer food_id) {
        getFoods.removeFood( food_id).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}