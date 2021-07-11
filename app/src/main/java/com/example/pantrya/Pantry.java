package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pantry extends AppCompatActivity {

    Button btnLout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        btnLout = findViewById(R.id.btnLogout);
        btnLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
                pref.edit().clear().commit();

                Intent intent = new Intent(getApplicationContext(), RedirectAct.class);
                startActivity(intent);
                finish();

            }
        });






    }
}