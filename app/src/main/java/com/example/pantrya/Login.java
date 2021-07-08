package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername , textInputEditTextPassword;
    Button buttonRegistrar, buttonLogon;
    String username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.usuarioL);
        textInputEditTextPassword = findViewById(R.id.passwordL);

        buttonRegistrar = findViewById(R.id.btnRegist);
        buttonLogon = findViewById(R.id.btnLogin);
        fetchPref();


        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Insertar.class);
                startActivity(intent);
                finish();

            }
        });

        buttonLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!username.equals("") && !password.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("https://pantryaidphp.000webhostapp.com/loginsign/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")){
                                        guardarPref();
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Pantry.class);
                                        startActivity(intent);
                                        finish();
                                    }else   {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else   {
                    Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarPref(){
        SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("usuario",username);
        editor.putString("password",password);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    private void fetchPref(){
        SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        textInputEditTextUsername.setText(pref.getString("usuario", ""));
        textInputEditTextPassword.setText(pref.getString("password", ""));


    }
}











