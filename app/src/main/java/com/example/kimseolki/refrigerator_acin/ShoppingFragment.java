package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kimseolki.refrigerator_acin.model.Shopping;
import com.example.kimseolki.refrigerator_acin.service.GetShopping;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class ShoppingFragment extends Fragment implements ShoppingListAdapter.ListBtnClickListener{
    private static final String TAG = "ShoppingFragment";
    private GetShopping getShopping;
    ListView lvShopping;
    ArrayList<Integer> shoppoing_id = new ArrayList<Integer>();
    List<Shopping> shopping;
    ShoppingListAdapter shopping_adapter;
    ImageButton ibAdd;
    Integer sId;
    private purchaseDialog mPurchaseDialog;
    String name;


    @Override
    public void onStart() {
        /* Fragment 생성시 loadFoods() 호출 */
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        loadShoppings();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.activity_shopping,container,false);

        FloatingActionButton fab1 = (FloatingActionButton) rootview.findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ShoppingRegister.class);
                startActivity(in);
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getShopping = retrofit.create(GetShopping.class);
        loadShoppings();

        lvShopping = (ListView) rootview.findViewById(R.id.lvShowpingList);
        lvShopping.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /* 기존 식재료 데이터 RefrigeratorModify로 전달 */
                Intent tIntent = new Intent(getContext(), ShoppingRegister.class);
                Bundle bundle = new Bundle();
                Integer shopping_id = shopping.get(i).getShoppingId();
                String shopping_type = shopping.get(i).getFoodType();
                String shopping_name = shopping.get(i).getShoppingName();
                String shopping_memo = shopping.get(i).getShoppingMemo();
                bundle.putSerializable("shopping1", (Serializable) shopping_id);
                bundle.putSerializable("shopping2", (Serializable) shopping_type);
                bundle.putSerializable("shopping3", (Serializable) shopping_name);
                bundle.putSerializable("shopping4", (Serializable) shopping_memo);
                tIntent.putExtras(bundle);
                startActivity(tIntent); // 식재료 수정 액티비티(RefrigeratorModify) 전환
            }
        });

        registerForContextMenu(lvShopping);
        return rootview;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d(TAG, "onContextItemCreate");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.activity_lcilckmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected");
        AdapterView.AdapterContextMenuInfo cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.mDelete: // 삭제
                cmenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                shopping.remove(cmenuInfo.position);
                shopping_adapter.notifyDataSetChanged();
                Integer f = shoppoing_id.get(cmenuInfo.position);       //Click한 Item에 position값을 이용하여 fId에 저장되어있는 Click한 Item의 food_number값을 저장
                shoppoing_id.remove(cmenuInfo.position);                 //DB에 삭제했지만 f 라는 arraylist에는 삭제되지 않았기때문에 삭제해줌.
                removeShopping(f);                                  // DB에 삭제하는 함수
                return true;
            default:
                Log.d(TAG, "Modify6");
                return true;
        }
    }

    private void removeShopping(Integer f) {
        getShopping.removeShopping(f).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void loadShoppings() {
        Call<List<Shopping>> call = getShopping.all();
        call.enqueue(new Callback<List<Shopping>>() {
            @Override
            public void onResponse(Call<List<Shopping>> call, Response<List<Shopping>> response) {
                shopping = response.body();
                displayResult(shopping);
                if(shoppoing_id != null) {
                    shoppoing_id.clear();
                }
                for(int i=0; i < shopping.size(); i++) {
                    shoppoing_id.add(shopping.get(i).getShoppingId());
                }
            }
            @Override
            public void onFailure(Call<List<Shopping>> call, Throwable t) {
            }
        });
    }

            private void displayResult(List<Shopping> shopping) {
                if (shopping != null) {
                    shopping_adapter = new ShoppingListAdapter(getActivity(), shopping, this);
                    lvShopping.setAdapter(shopping_adapter);

                } else {

        }
    }

    @Override
    public void onListBtnClick(int position) {
        //Toast.makeText(getActivity(), "ㅎㅇ", Toast.LENGTH_SHORT).show();
        mPurchaseDialog = new purchaseDialog(getContext(), mEmartClickListner, mGmarketClickListener, mHomeplusClickListener, m11StreetClickListener);
        mPurchaseDialog.setCanceledOnTouchOutside(false);
        mPurchaseDialog.show();
        name = shopping.get(position).getShoppingName().toString();
        Log.d(TAG, String.valueOf(sId));
    }

    private View.OnClickListener mEmartClickListner = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String recipeURL = "http://m.emart.ssg.com/search.ssg?query="+name;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(recipeURL);
            intent.setData(u);
            startActivity(intent);
            mPurchaseDialog.dismiss();
        }
    };
    private View.OnClickListener mGmarketClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String recipeURL = "http://mobile.gmarket.co.kr/Search/Search?topKeyword="+name;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(recipeURL);
            intent.setData(u);
            startActivity(intent);
            mPurchaseDialog.dismiss();
        }
    };
    private View.OnClickListener mHomeplusClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String recipeURL = "http://m.homeplus.co.kr/search/result.do?comm_query="+name+"&search_preurl=http%3A%2F%2Fm.homeplus.co.kr%2Findex.do";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(recipeURL);
            intent.setData(u);
            startActivity(intent);
            mPurchaseDialog.dismiss();
        }
    };
    private View.OnClickListener m11StreetClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String keyword ="";
            try {
                keyword = URLEncoder.encode(name, "euc-kr");
            } catch (UnsupportedEncodingException e1){
                e1.printStackTrace();
            }
            String recipeURL = "http://m.11st.co.kr/MW/Search/searchProduct.tmall?decSearchKeyword=%BB%E7%B0%FA&searchType=&searchKeyword="+keyword+"&listType=&sortCd=NP&showAdvProducts=Y&dispCtgrNo=&dispCtgrLevel=&pageNo=&inKeyword=&withoutKeyword=&previousKwd=&previousExcptKwd=&fromPrice=&toPrice=&discountCheck=&noInterestCheck=&freeDeliveryCheck=&worldDeliveryCheck=&isSearchInSearch=&isCategoryInSearch=&isBrandInSearch=&isOneCategorySearch=&brandName=&brandItemName=&brandTitle=&lCategoryNos=&mCategoryNos=&sCategoryNos=&dCategoryNos=&pageSize=0";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(recipeURL);
            intent.setData(u);
            startActivity(intent);
            mPurchaseDialog.dismiss();
        }
    };
}