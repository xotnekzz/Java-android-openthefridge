package com.example.kimseolki.refrigerator_acin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.PFood;
import com.example.kimseolki.refrigerator_acin.service.PostFoods;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.GregorianCalendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by userpc on 2017-04-21.
 */

public class RefrigeratorModify extends AppCompatActivity {
    private static final String TAG = "RefrigeratorModify";

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private int TAKE_CAMERA = 1; // 카메라 리턴 코드값 설정
    private int TAKE_GALLERY = 2; // 앨범선택에 대한 리턴 코드값 설정

    final int DIALOG_DATE1 = 3; //datapicker dialog 구분 코드값 설정
    final int DIALOG_DATE2 = 4;

    Intent intent;
    public static ImageView imageView;
    public Spinner type ;
    public EditText name ;
    public EditText purcharse ;
    public EditText exdate ;
    public Spinner location;
    public ImageButton btn_qrcode;
    public ImageButton btn_getImage;
    public Button recipeButton;
    public Button purchaseButton;
    purchaseDialog mPurchaseDialog;
    RefrigeratorModify.register_image_Dialog image_Dialog;
    int year, month, day, hour, minute;
    String modify3;
    String modify7; // 이미지


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmodify);

        imageView = (ImageView) findViewById(R.id.imageView);
        type = (Spinner) findViewById(R.id.sptype);
        name = (EditText) findViewById(R.id.etname);
        purcharse = (EditText) findViewById(R.id.etpurcharse);
        exdate = (EditText) findViewById(R.id.etexdate);
        location = (Spinner) findViewById(R.id.splocation);
        btn_qrcode = (ImageButton) findViewById(R.id.btn_qrCode);
        recipeButton = (Button) findViewById(R.id.recipeButton);
        purchaseButton = (Button) findViewById(R.id.purchaseButton);
        purcharse.setInputType(InputType.TYPE_NULL);
        exdate.setInputType(InputType.TYPE_NULL);
        //onClick시 datepicker 다이얼로그 호출
        purcharse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE1);
            }
        });
        exdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE2);
            }
        });
        final Activity activity = this;


        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        btn_getImage = (ImageButton) findViewById(R.id.btn_image1);
        btn_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_Dialog = new RefrigeratorModify.register_image_Dialog(RefrigeratorModify.this);
                image_Dialog.setCanceledOnTouchOutside(false);
                image_Dialog.show();
            }
        });

        intent = getIntent();
        //Tab에서 보내는 Intent정보를 불러옴
        Log.d(TAG, String.valueOf(intent));
        String modify2=intent.getStringExtra("foods2");
        modify3 = intent.getStringExtra("foods3");
        String modify4=intent.getStringExtra("foods4"); //구매일
        String modify5=intent.getStringExtra("foods5"); //유통기한
        String modify6=intent.getStringExtra("foods6");
        modify7=intent.getStringExtra("foods7"); // 이미지
        Integer modify1= (Integer) intent.getSerializableExtra("foods1");
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name.setText("");
        name.setText(modify3);
        exdate.setText("");
        exdate.setText(modify5);
        purcharse.setText("");
        purcharse.setText(modify4);
        imageView.setImageResource(Integer.parseInt(modify7));


        Log.d(TAG, "test6");
        Log.d(TAG, "this is oncreate EditText test");
        Log.d(TAG, "this is setText after EditText test");
        //분류에서 어댑터를 입혀 각각 불러온정보에 맞는 index값 호출
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.FOOD_TYPES,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        if(modify2.equals("육류")) {
            type.setSelection(0);
        }
        else if (modify2.equals("어류")){
            type.setSelection(1);
        }
        else if (modify2.equals("과일류")){
            type.setSelection(2);
        }
        else if (modify2.equals("야채류")){
            type.setSelection(3);
        }
        else if (modify2.equals("유제품류")){
            type.setSelection(4);
        }
        else if (modify2.equals("음료류")){
            type.setSelection(5);
        }
        else if (modify2.equals("소스류")){
            type.setSelection(6);
        }
        else if (modify2.equals("기타")){
            type.setSelection(7);
        }

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //장소에서 어댑터를 입혀 각각 불러온정보에 맞는 index값 호출
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.FOOD_LOCATIONS,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter1);
        if(modify6.equals("냉장")) {
            location.setSelection(0);
        }
        else if (modify6.equals("냉동")){
            location.setSelection(1);
        }
        else if (modify6.equals("실온")){
            location.setSelection(2);
        }


        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //QR코드 스캐너 호출
        btn_qrcode = (ImageButton) findViewById(R.id.btn_qrCode);
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator Integrator = new IntentIntegrator(activity);
                Integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                Integrator.setPrompt("Scan");
                Integrator.setCameraId(0);
                Integrator.setBeepEnabled(false);
                Integrator.setBarcodeImageEnabled(false);
                Integrator.initiateScan();
            }
        });
        // 추천 레시피
        recipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recipeIntent = new Intent(getApplicationContext(), Recipe_recommand_activity.class);
                recipeIntent.putExtra("food", modify3);
                startActivity(recipeIntent);
            }
        });

        // 구매하기
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPurchaseDialog = new purchaseDialog(RefrigeratorModify.this, mEmartClickListner, mGmarketClickListener, mHomeplusClickListener, m11StreetClickListener);
                mPurchaseDialog.setCanceledOnTouchOutside(false);
                mPurchaseDialog.show();
            }
        });
    }
    /* 구매 다이얼로그 이벤트 */
    private View.OnClickListener mEmartClickListner = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String recipeURL = "http://m.emart.ssg.com/search.ssg?query="+modify3;
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
            String recipeURL = "http://mobile.gmarket.co.kr/Search/Search?topKeyword="+modify3;
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
            String recipeURL = "http://m.homeplus.co.kr/search/result.do?comm_query="+modify3+"&search_preurl=http%3A%2F%2Fm.homeplus.co.kr%2Findex.do";
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
                keyword = URLEncoder.encode(modify3, "euc-kr");
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

    @Override
    @Deprecated
    //DAtePickder Dialog 동작
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DATE1:
                DatePickerDialog dpd = new DatePickerDialog
                        (RefrigeratorModify.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        purcharse.setText("");
                                        purcharse.setText(purcharse.getText() + String.valueOf(year) + ". "
                                                + String.valueOf(monthOfYear+1)+". "+ String.valueOf(dayOfMonth) );}
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                year, month, day); // 기본값 연월일
                return dpd;
            case DIALOG_DATE2:
                DatePickerDialog dpd2 =new DatePickerDialog
                        (RefrigeratorModify.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {
                                        exdate.setText("");
                                        exdate.setText(exdate.getText() + String.valueOf(year) + ". "
                                                + String.valueOf(monthOfYear+1)+". "+ String.valueOf(dayOfMonth) );}
                                }
                                ,year, month, day // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                        ); // 기본값 연월일
                return dpd2;
        }


        return super.onCreateDialog(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //QR코드를 찍어 불러온 스트링을 나누어 각각의 EditText에 set시킴
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "해당 QR코드를 찾을 수 없습니다.", Toast.LENGTH_LONG).show();
            } else {
                String qr = result.getContents();
                String[] qrarray;
                qrarray = qr.split(";");
                name.setText(qrarray[1]);
                ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.FOOD_TYPES,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                type.setAdapter(adapter);
                if(qrarray[0].equals("육류")) {
                    type.setSelection(0);
                }
                else if (qrarray[0].equals("어류")){
                    type.setSelection(1);
                }
                else if (qrarray[0].equals("과일류")){
                    type.setSelection(2);
                }
                else if (qrarray[0].equals("야채류")){
                    type.setSelection(3);
                }
                else if (qrarray[0].equals("유제품류")){
                    type.setSelection(4);
                }
                else if (qrarray[0].equals("음료류")){
                    type.setSelection(5);
                }
                else if (qrarray[0].equals("소스류")) {
                    type.setSelection(6);
                }
                else if (qrarray[0].equals("기타")){
                        type.setSelection(7);
                    }

                purcharse.setText(qrarray[2]);
                exdate.setText(qrarray[3]);

                ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.FOOD_LOCATIONS,android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                location.setAdapter(adapter1);
                if(qrarray[4].equals("냉장")) {
                    location.setSelection(0);
                }
                else if (qrarray[4].equals("냉동")){
                    location.setSelection(1);
                }
                else if (qrarray[4].equals("실온")){
                    location.setSelection(2);
                }
                imageView.setImageResource(Integer.parseInt(qrarray[5]));
            }
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_CAMERA) {
                if( data != null )
                {
                    Log.e("Test", "result = " + data);
                    Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
                    if( thumbnail != null )
                    {
                        ImageView Imageview = (ImageView) findViewById(R.id.imageView);
                        Imageview.setImageBitmap(thumbnail);
                    }
                }

            } else if (requestCode == TAKE_GALLERY) {
                if( data != null )
                {
                    Log.e("Test", "result = " + data);

                    Uri thumbnail = data.getData();
                    if( thumbnail != null )
                    {
                        ImageView Imageview = (ImageView) findViewById(R.id.imageView);
                        Imageview.setImageURI(thumbnail);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_modify, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.atmodify:
                //String pre_image = FoodInfo.getInstance().getPre_image();// modify7
                String food_image = modify7; // 선택한 이미지
                PFood pFood = new PFood(
                        type.getSelectedItem().toString(),
                        name.getText().toString(),
                        purcharse.getText().toString(),
                        exdate.getText().toString(),
                        location.getSelectedItem().toString(),
                        food_image
                );
                sendNetworkRequest(pFood);      //입력된 값들을 객체로 저장하여 보냄
                finish();
                return true;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected:android.R.id.home");
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void sendNetworkRequest(PFood pFood) {
        Log.d(TAG, "수정전");
//        int id = Integer.parseInt(modify1);
        intent = getIntent();
        Integer modify1= (Integer) intent.getSerializableExtra("foods1");       //TAB에서 눌려진 food_number저장
        Log.d(TAG, "수정전2");
        Log.d(TAG, String.valueOf(modify1));

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostFoods client = retrofit.create(PostFoods.class);
        Call<PFood> call= client.updateAccount(pFood, modify1);     //DB에 이미 저장된 데이터를 새롭게 수정함

        call.enqueue(new Callback<PFood>() {
            @Override
            public void onResponse(Call<PFood> call, Response<PFood> response) {
                Toast.makeText(RefrigeratorModify.this, "수정완료" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PFood> call, Throwable t) {
                Toast.makeText(RefrigeratorModify.this, "수정실패",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class register_image_Dialog extends Dialog {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
            lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lpWindow.dimAmount = 0.8f;
            getWindow().setAttributes(lpWindow);

            setContentView(R.layout.dialog_register_image);
            ListView lvFood = (ListView) findViewById(R.id.lvFoodimg);
            final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.food_type, R.layout.layout_simple_listview);
            lvFood.setAdapter(adapter);

            lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch(position)
                    {
                        case 0:
                            FoodInfo.getInstance().setFood_type(0);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog.setCanceledOnTouchOutside(false);
                            gridimage_Dialog.show();
                            dismiss();
                            break;
                        case 1:
                            FoodInfo.getInstance().setFood_type(1);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog1 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog1.setCanceledOnTouchOutside(false);
                            gridimage_Dialog1.show();
                            dismiss();
                            break;
                        case 2:
                            FoodInfo.getInstance().setFood_type(2);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog2 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog2.setCanceledOnTouchOutside(false);
                            gridimage_Dialog2.show();
                            dismiss();
                            break;
                        case 3:
                            FoodInfo.getInstance().setFood_type(3);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog3 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog3.setCanceledOnTouchOutside(false);
                            gridimage_Dialog3.show();
                            dismiss();
                            break;
                        case 4:
                            FoodInfo.getInstance().setFood_type(4);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog4 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog4.setCanceledOnTouchOutside(false);
                            gridimage_Dialog4.show();
                            dismiss();
                            break;
                        case 5:
                            FoodInfo.getInstance().setFood_type(5);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog5 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog5.setCanceledOnTouchOutside(false);
                            gridimage_Dialog5.show();
                            dismiss();
                            break;
                        case 6:
                            FoodInfo.getInstance().setFood_type(6);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog6 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog6.setCanceledOnTouchOutside(false);
                            gridimage_Dialog6.show();
                            dismiss();
                            break;
                        case 7:
                            FoodInfo.getInstance().setFood_type(7);
                            RefrigeratorModify.gridImageDialog gridimage_Dialog7 = new RefrigeratorModify.gridImageDialog(getContext());
                            gridimage_Dialog7.setCanceledOnTouchOutside(false);
                            gridimage_Dialog7.show();
                            dismiss();
                            break;
                    }
                }
            });

        }

        public register_image_Dialog(@NonNull Context context) {
            super(context, android.R.style.Theme_Translucent_NoTitleBar);
        }
    }

    /**
     * Created by 6201P-03 on 2017-06-18.
     */

    public static class gridImageDialog extends Dialog {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int food_type = FoodInfo.getInstance().getFood_type();
            Log.e("gridDialog", String.valueOf(food_type));
            Integer[] imgs = {};
            if(food_type ==0){
                imgs = new Integer[]{
                        R.drawable.meat_01, R.drawable.meat_02, R.drawable.meat_03, R.drawable.meat_04
                };
            }else if(food_type==1)
            {
                imgs = new Integer[]{
                        R.drawable.fish_01, R.drawable.fish_02, R.drawable.fish_03, R.drawable.fish_04, R.drawable.fish_05
                };
            }
            else if(food_type==2)
            {
                imgs = new Integer[]{
                        R.drawable.fruits_01, R.drawable.fruits_02, R.drawable.fruits_03, R.drawable.fruits_04, R.drawable.fruits_05,
                        R.drawable.fruits_06, R.drawable.fruits_07, R.drawable.fruits_08, R.drawable.fruits_09, R.drawable.fruits_10, R.drawable.fruits_11
                };
            }
            else if(food_type==3)
            {
                imgs = new Integer[] {
                        R.drawable.vegitable_01, R.drawable.vegitable_02, R.drawable.vegitable_03, R.drawable.vegitable_04, R.drawable.vegitable_05,
                        R.drawable.vegitable_06, R.drawable.vegitable_07, R.drawable.vegitable_08, R.drawable.vegitable_09, R.drawable.vegitable_10,
                        R.drawable.vegitable_11
                };
            }
            else if(food_type==4)
            {
                imgs = new Integer[]{
                        R.drawable.milk_01, R.drawable.milk_02, R.drawable.milk_03, R.drawable.milk_04, R.drawable.milk_05
                };
            }
            else if(food_type==5)
            {
                imgs = new Integer[]{
                        R.drawable.beverage_01, R.drawable.beverage_02, R.drawable.beverage_03, R.drawable.beverage_04, R.drawable.beverage_05, R.drawable.beverage_06,
                        R.drawable.beverage_07, R.drawable.beverage_08
                };
            }
            else if(food_type==6)
            {
                imgs = new Integer[]{
                        R.drawable.source_01, R.drawable.source_02, R.drawable.source_03, R.drawable.source_04, R.drawable.source_05, R.drawable.source_06
                };
            }
            else if(food_type==7)
            {
                imgs = new Integer[]{
                        R.drawable.etc_01, R.drawable.etc_02, R.drawable.etc_03, R.drawable.etc_04, R.drawable.etc_05,
                        R.drawable.etc_06, R.drawable.etc_07, R.drawable.etc_08, R.drawable.etc_09, R.drawable.etc_10, R.drawable.etc_11
                };
            }
            WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
            lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lpWindow.dimAmount = 0.8f;
            getWindow().setAttributes(lpWindow);

            setContentView(R.layout.dialog_food_type);

            ImageListAdapter imageGridAdapter = new ImageListAdapter(getContext(), R.layout.gridrow, imgs);
            GridView gvFood_img = (GridView)findViewById(R.id.gvFood);
            gvFood_img.setAdapter(imageGridAdapter);
            gvFood_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("imageid", String.valueOf(id));
                    Log.e("resource", parent.getAdapter().getItem(position).toString());
                    int resID = FoodInfo.getInstance().getFood_type()+position;
                    //Toast.makeText(getContext(), FoodInfo.getInstance().getFood_type()+position, Toast.LENGTH_SHORT).show();
                    FoodInfo.getInstance().setType_name(resID);
                    FoodInfo.getInstance().setPre_image(String.valueOf(resID));
                    Log.e("fish", String.valueOf(resID));
                    dismiss();
                    imageView.setImageResource(FoodInfo.getInstance().getType_name());
                }
            });

        }

        public gridImageDialog(@NonNull Context context) {
            super(context, android.R.style.Theme_Translucent_NoTitleBar);
        }

    }
}