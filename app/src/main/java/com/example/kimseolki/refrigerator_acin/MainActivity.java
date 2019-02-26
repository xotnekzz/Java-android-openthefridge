package com.example.kimseolki.refrigerator_acin;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.Food;
import com.example.kimseolki.refrigerator_acin.model.Food_location;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private static int ONE_MINUTE = 5626;
    private TextView resultTv;
    private GetFoods getFoods;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<String> food_list;
    ArrayAdapter<String> food_adapter;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    private Fragment mFragment;
    int Navigation_id;
    FloatingActionButton fab;
    MenuItem rItem;
    MenuItem refrigerator;
    NavigationView navigationView;
    int TabPosition=0;
    int year, month, day;
    int eYear, eMonth, eDay;
    ArrayList<Food_location> food_location;
    ArrayList<String> s;
    public static final String ENDPOINT_URLF = "http://192.168.63.18:8000/";
    String IP;
    String PORT;
    String IP_address;
    TabPagerAdapter pagerAdapter;
    private Fragment mFragment1;
    SharedPreferences preferences;
    String gettime;
    String getdate;
    boolean pushonoff;
    String extime;
    String exdate;
    String[] extimes;
    String[] extimes2;
    String[] exdates;
    int intextimes;
    int intextimes2;
    int intexdates;
    int total;

    @Override
    protected void onResume() {
        super.onResume();
        Alram_fridge();
        //pagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* 앱 시작시 Tab1 Fragment 사용*/
        if(mFragment == null ) {
            mFragment = new Tab1();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, mFragment);
            ft.commit();
        }
        /* 백버튼 눌렀을 때 Tab1 Fragment 저장*/
        mFragment1 = new Tab1();
        loadFoods();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadFoods();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences  auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        IP = auto.getString("inputIP", "");
        PORT = auto.getString("inputPORT", "");
        IP_address = "http://"+IP+":"+PORT+"/";

        LoginInfo.getInstance().setIp_address(IP_address);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(IP_address)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getFoods = retrofit.create(GetFoods.class);
        loadFoods();
        //Navigation_id = R.id.nav_refigearator; // 초기화면 프래크먼트 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sIntent = new Intent(getApplicationContext(), RefrigeratorRegister.class);
                startActivity(sIntent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("냉장"));
        tabLayout.addTab(tabLayout.newTab().setText("냉동"));
        tabLayout.addTab(tabLayout.newTab().setText("실온"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        tabLayout.setTabTextColors(Color.parseColor("#c6e1fb"),Color.parseColor("#5292cf")); //안눌린색, 눌린색
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               tabLayout.setTabTextColors(Color.parseColor("#c6e1fb"),Color.parseColor("#5292cf"));
                TabPosition = tab.getPosition();
                Log.d("tabposition", String.valueOf(TabPosition));

                /* 냉장고 관리 fragment 기억 */
                if(TabPosition == 0){
                    mFragment1 = new Tab1();
                }else if(TabPosition == 1){
                    mFragment1 = new Tab2();
                }else if(TabPosition == 2){
                    mFragment1 = new Tab3();
                }


                if(tab.getPosition() == 0) {
                    mFragment = new Tab1();
                } else if(tab.getPosition() == 1){
                    mFragment = new Tab2();
                } else if(tab.getPosition() == 2){
                    mFragment = new Tab3();
                }

                if (mFragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_fragment_layout, mFragment);
                    // ft.addToBackStack(null); // 스택에 이전 프래그먼트 저장
                    ft.commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        refrigerator = menu.findItem(R.id.nav_refigearator);
        onNavigationItemSelected(refrigerator);
    }

    void Alram_fridge(){
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        gettime = preferences.getString("time","");
        getdate = preferences.getString("date","");
        pushonoff = preferences.getBoolean("push",false);

        if(gettime== null || gettime.equals("") ||pushonoff==false)
        {

            Log.e(TAG,"저장된 설정값이 없습니다.");

        }
        else{
            Log.e(TAG,"알람이 실행됩니다.");
            extime = gettime;
            exdate = getdate;
            extimes = extime.split("시");            //extimes[0] = 00(시)
            extimes2 = extimes[1].split("분");       //extimes2[0] = 00(분)
            exdates = exdate.split("일");            //exdates[0] = 00일전
            intextimes = Integer.parseInt(extimes[0]);
            intextimes2 = Integer.parseInt(extimes2[0]);
            intexdates = Integer.parseInt(exdates[0]);
            new AlarmHATT(getApplicationContext()).Alarm();
        }
    }

    private void loadFoods() {
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
        food_location = new ArrayList<Food_location>();
        if (foods != null) {
            for(int i=0; i<foods.size(); i++) {  /* 유통기한 날짜 설정 */
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
                int d_day = (int) (currentTime - expriation) +1;

                Food_location food_locations = new Food_location(foods.get(i).getFood_name(),d_day,foods.get(i).getFood_purchase());
                food_location.add(food_locations);
            }
            FoodInfo.getInstance().setFood_location(food_location);
            GetFood_exdate();
        }
    }
    public int GetFood_exdate() {
        total=0;
        for(int i = 0; i < this.food_location.size(); i++){
            if(this.food_location.get(i).getD_day() >= (-intexdates)){
                total++;
            }
            FoodInfo.getInstance().setExdate_total(total);
            FoodInfo.getInstance().setSettingdate(intexdates);
        }
        return total;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here..
        loadFoods();
        Navigation_id = item.getItemId();
        if (item.isChecked()) item.setChecked(false);

        String title = getString(R.string.app_name);
        if(Navigation_id == R.id.nav_refigearator) {
            //mFragment = new Tab1();
            //mFragment1 = new Tab1();
            rItem = item;
            tabLayout.setVisibility(View.VISIBLE);
            //viewPager.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            Log.d(TAG, String.valueOf(TabPosition));
            title = "냉장고 관리";
        }
        else if (Navigation_id == R.id.nav_expirationdate) {
            tabLayout.setVisibility(View.GONE);
            //viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new ExpriationFragment();
            title = "유통기한";

        } else if (Navigation_id == R.id.nav_recipe) {
            tabLayout.setVisibility(View.GONE);
            //viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new RecipeFragment();
            title = "레시피";

        } else if (Navigation_id == R.id.nav_shoppinglist) {
            // Handle the camera action
            tabLayout.setVisibility(View.GONE);
            //viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new ShoppingFragment();
            title = "쇼핑리스트";

        } else if (Navigation_id == R.id.nav_hazardfood) {
            tabLayout.setVisibility(View.GONE);
            //viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new HealthInfoFragment();
            title = "건강정보";

        }else if (Navigation_id == R.id.nav_memo) {
            tabLayout.setVisibility(View.GONE);
            //  viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new MemoFragment();
            title = "메모";

        } else if (Navigation_id == R.id.nav_settings) {
            tabLayout.setVisibility(View.GONE);
            //viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new SettingsFragment();
            title = "설정";
        }

        if (mFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(Navigation_id == R.id.nav_refigearator)
            {
                Log.d(TAG, String.valueOf(mFragment1));
                ft.replace(R.id.content_fragment_layout, mFragment1);
                ft.commit();
                // ft.addToBackStack(null); // 스택에 이전 프래그먼트 저장
            }
            else {
                ft.replace(R.id.content_fragment_layout, mFragment);
                // ft.addToBackStack(null); // 스택에 이전 프래그먼트 저장
                ft.commit();
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        // 백버튼시 냉장고 관리 Fragment로 전환
        if(Navigation_id != R.id.nav_refigearator){
            if(rItem!=null) {
                onNavigationItemSelected(rItem);
                navigationView.setCheckedItem(R.id.nav_refigearator);
            }
        }
        // 냉장고 관리 Fragment
        else if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }


    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context = context;
        }

        public void Alarm() {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, com.example.kimseolki.refrigerator_acin.BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent,0);

            Calendar calendar = Calendar.getInstance();
            Calendar calendar1 = Calendar.getInstance();

            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DATE), calendar1.get(Calendar.HOUR_OF_DAY),
                    calendar1.get(Calendar.MINUTE), 00);
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), intextimes, intextimes2, 00);

            Log.d(TAG, String.valueOf(calendar1.getTime()));
            String now=calendar1.getTime().toString();

            int hour1 = now.charAt(11)-48;
            int hour2 = now.charAt(12)-48;

            int minute1 = now.charAt(14)-48;
            int minute2 = now.charAt(15)-48;

            int sethour =(hour1*10 +hour2)*60;
            int setminute = minute1*10+minute2;

            if(total>0||(sethour+setminute)<((intextimes*60)+intextimes2)) {
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
        }


    }
}
