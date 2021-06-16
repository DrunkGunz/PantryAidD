package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Insertar extends AppCompatActivity {


    TextInputEditText textInputEditTextUsername , textInputEditTextEmail, textInputEditTextDireccion, textInputEditTextPassword;
    Button buttonRegistrar, buttonLogon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        textInputEditTextUsername = findViewById(R.id.usuarioR);
        textInputEditTextEmail = findViewById(R.id.emailR);
        textInputEditTextDireccion = findViewById(R.id.direccionR);
        textInputEditTextPassword = findViewById(R.id.passwordR);

        buttonRegistrar = findViewById(R.id.btnSignUp);
        buttonLogon = findViewById(R.id.btnLogon);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, email, direccion, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                direccion = String.valueOf(textInputEditTextDireccion.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!username.equals("") && !email.equals("") && !direccion.equals("") && !password.equals("")) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "email";
                            field[2] = "direccion";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = email;
                            data[2] = direccion;
                            data[3] = password;

                            PutData putData = new PutData("https://192.168.18.7/loginsign/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
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









}