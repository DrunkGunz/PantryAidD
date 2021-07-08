package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class RedirectAct extends AppCompatActivity {

    ProgressBar barra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redirect);
        barra = findViewById(R.id.prbr_1);
        barra.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
                boolean sesion = pref.getBoolean("sesion", false);
                if (sesion){
                    Intent intent = new Intent(getApplicationContext(),Pantry.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);


    }
}