package com.example.kimseolki.refrigerator_acin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 6201P-03 on 2017-06-16.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etIP = (EditText) findViewById(R.id.etIP);
        final EditText etPort = (EditText) findViewById(R.id.etPort);
        Button btInput = (Button) findViewById(R.id.btInput);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        String ip = auto.getString("inputIP", null);
        String port = auto.getString("inputPORT", null);

        if (ip != null && port != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(ip == null && port == null){
            btInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor autoLogin = auto.edit();
                    autoLogin.putString("inputIP", etIP.getText().toString());
                    autoLogin.putString("inputPORT", etPort.getText().toString());
                    autoLogin.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
