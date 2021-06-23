package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Insertar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText textInputEditTextUsername , textInputEditTextEmail, textInputEditTextDireccion, textInputEditTextPassword;
    Button buttonRegistrar, buttonLogon;
    Spinner spinnerRegion, spinnerProvincia, spinnerComuna;
    String codCO;

    ArrayList<String> listaRegion = new ArrayList<>();
    ArrayList<String> listaProvincia = new ArrayList<>();
    ArrayList<String> listaComuna = new ArrayList<>();
    HashMap<String ,String>  hmComuna = new HashMap<String,String>();

    ArrayAdapter<String> adapterRegion;
    ArrayAdapter<String> adapterProvincia;
    ArrayAdapter<String> adapterComuna;

    RequestQueue requestQueue;



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

        requestQueue = Volley.newRequestQueue(this);
        spinnerRegion = findViewById(R.id.spnRegion);
        spinnerProvincia = findViewById(R.id.spnProvincia);
        spinnerComuna = findViewById(R.id.spnComuna);

        String url = "http://192.168.18.7/loginsign/obtnrRegion.php";


        //Rellenar-> rellena el spinner
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("region");
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String nombreRegion =  jsonObject.optString("NOMBRE");
                        listaRegion.add(nombreRegion);
                        adapterRegion = new ArrayAdapter<>(Insertar.this, android.R.layout.simple_spinner_item, listaRegion);
                        adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerRegion.setAdapter(adapterRegion);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerRegion.setOnItemSelectedListener(this);





        //login-> redirecciona a la pagina de login
        buttonLogon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        //registrar-> empuja los datos al php para ingresarlos a la base de datos
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, email, direccion, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                direccion = String.valueOf(textInputEditTextDireccion.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!username.equals("") && !email.equals("") && !direccion.equals("") && !password.equals("") && !codCO.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "username";
                            field[1] = "email";
                            field[2] = "direccion";
                            field[3] = "password";
                            field[4] = "COD_CO";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = username;
                            data[1] = email;
                            data[2] = direccion;
                            data[3] = password;
                            data[4] = codCO;
                            PutData putData = new PutData("http://192.168.18.7/loginsign/signup.php", "POST", field, data);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch(adapterView.getId()){
            case R.id.spnRegion:
                    listaProvincia.clear();
                    String selectdRegion = adapterView.getSelectedItem().toString();
                    String url = "http://192.168.18.7/loginsign/obtnrProvin.php?selectdRegion="+selectdRegion;
                    requestQueue = Volley.newRequestQueue(this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("provincia");
                                for (int i=0; i<jsonArray.length();i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String nombreProvin = jsonObject.optString("NOMBRE");
                                    listaProvincia.add(nombreProvin);
                                    adapterProvincia = new ArrayAdapter<>(Insertar.this, android.R.layout.simple_spinner_item, listaProvincia);
                                    adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerProvincia.setAdapter(adapterProvincia);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                    spinnerProvincia.setOnItemSelectedListener(this);
                break;

            case R.id.spnProvincia:
                    listaComuna.clear();
                    String selectdProvin = adapterView.getSelectedItem().toString();
                     url = "http://192.168.18.7/loginsign/obtnrComu.php?selectdProvin="+selectdProvin;
                    requestQueue = Volley.newRequestQueue(this);
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray = response.getJSONArray("comuna");
                                    hmComuna.clear();

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String cod_CO = jsonObject.optString("COD_CO");
                                        String nombreComuna = jsonObject.optString("NOMBRE");
                                        listaComuna.add(nombreComuna);
                                        hmComuna.put(nombreComuna, cod_CO);
                                        adapterComuna = new ArrayAdapter<>(Insertar.this, android.R.layout.simple_spinner_item, listaComuna);
                                        adapterComuna.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinnerComuna.setAdapter(adapterComuna);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                    requestQueue.add(jsonObjectRequest);
                    spinnerComuna.setOnItemSelectedListener(this);
                break;

            case  R.id.spnComuna:
                String nombCom = adapterView.getItemAtPosition(position).toString();
                codCO = hmComuna.get(nombCom);
                Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show();
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}