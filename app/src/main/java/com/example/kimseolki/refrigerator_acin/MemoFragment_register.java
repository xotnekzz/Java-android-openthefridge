package com.example.kimseolki.refrigerator_acin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.Memo;
import com.example.kimseolki.refrigerator_acin.service.PostMemo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MemoFragment_register extends AppCompatActivity {

    public EditText author;
    public EditText memo;
    private static final String TAG ="MemoFragment" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        author = (EditText) findViewById(R.id.etMauthor);
        memo = (EditText) findViewById(R.id.etMmemo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_register,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.atregister:
                Memo memo1 = new Memo(
                        author.getText().toString(),
                        memo.getText().toString()
                );
                Log.d(TAG, "되냐");
                sendNetworkRequest(memo1);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void sendNetworkRequest(Memo memo) {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginInfo.getInstance().getIP_address())             //서버 주소입력
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostMemo client = retrofit.create(PostMemo.class);
        Call<Memo> call= client.createAccount(memo);

        call.enqueue(new Callback<Memo>() {
            @Override
            public void onResponse(Call<Memo> call, Response<Memo> response) {
                Toast.makeText(MemoFragment_register.this, "등록" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Memo> call, Throwable t) {
                Toast.makeText(MemoFragment_register.this, "실패:(",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
