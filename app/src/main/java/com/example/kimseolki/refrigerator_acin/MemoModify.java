package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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


public class MemoModify extends AppCompatActivity{

    Intent intent;
    public EditText author;
    public EditText memoo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        author = (EditText) findViewById(R.id.etMauthor);
        memoo = (EditText) findViewById(R.id.etMmemo);

        intent = getIntent();

        String mauthor = intent.getStringExtra("memoauthor");
        String mmemo = intent.getStringExtra("memomemo");
        Integer mid = (Integer) intent.getSerializableExtra("memoid");

        author.setText("");
        author.setText(mauthor);
        memoo.setText("");
        memoo.setText(mmemo);
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
                Memo memo = new Memo(
                        author.getText().toString(),
                        memoo.getText().toString()
                );
                sendNetworkRequest(memo);
                finish();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendNetworkRequest(Memo memo){
        intent = getIntent();
        Integer mmodify = (Integer) intent.getSerializableExtra("memoid");

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(LoginInfo.getInstance().getIP_address())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());
        Retrofit retrofit = builder.build();
        PostMemo client = retrofit.create(PostMemo.class);
        Call<Memo> call = client.updateMemo(memo, mmodify);

        call.enqueue(new Callback<Memo>() {
            @Override
            public void onResponse(Call<Memo> call, Response<Memo> response) {
                Toast.makeText(MemoModify.this, "onResponse" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Memo> call, Throwable t) {
                Toast.makeText(MemoModify.this, "onFailure:(",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

