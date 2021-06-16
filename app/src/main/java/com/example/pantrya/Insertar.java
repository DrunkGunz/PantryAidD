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








        }









}