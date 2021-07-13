package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class addEnvase extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText textInputEditTextNombre, textInputEditTextGramo;

    String codEn;
    Spinner spinnerEnvase;
    Button buttonAddEN, buttonAsoc;

    ArrayList<String> listaEnvase = new ArrayList<>();
    HashMap<String ,String> hmEnvase = new HashMap<String,String>();
    ArrayAdapter<String> adapterEnvase;
    RequestQueue requestQueueA;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_envase);
        String codIn = getIntent().getStringExtra("codIN");

        textInputEditTextGramo = findViewById(R.id.cantGramo);
        textInputEditTextNombre = findViewById(R.id.nombGramo);
        buttonAddEN = findViewById(R.id.btnAddCustomEn);
        buttonAsoc = findViewById(R.id.btnAsocEn);
        spinnerEnvase = findViewById(R.id.spnEnvasesito);

        requestQueueA = Volley.newRequestQueue(this);

        String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrEnvasesolo.php";
        //String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrEnvase.php?selectIngrediente=Arroz%20Basmati";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("envase");
                    hmEnvase.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cod_tien = jsonObject.optString("COD_EN");
                        String nombreTien = jsonObject.optString("NOMBRE");
                        listaEnvase.add(nombreTien);
                        hmEnvase.put(nombreTien, cod_tien);
                        adapterEnvase = new ArrayAdapter<>(addEnvase.this, android.R.layout.simple_spinner_item, listaEnvase);
                        adapterEnvase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEnvase.setAdapter(adapterEnvase);
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
        requestQueueA.add(jsonObjectRequest);
        spinnerEnvase.setOnItemSelectedListener(this);


        buttonAddEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coding, nombGramo, gramonum;
                coding = codIn;
                nombGramo = String.valueOf(textInputEditTextNombre.getText());
                gramonum = String.valueOf(textInputEditTextGramo.getText());

                if (!codIn.equals("") && !nombGramo.equals("") && !gramonum.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[3];
                            field[0] = "codin";
                            field[1] = "nombGra";
                            field[2] = "gramo";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = coding;
                            data[1] = nombGramo;
                            data[2] = gramonum;
                            PutData putData = new PutData("https://pantryaidphp.000webhostapp.com/loginsign/signEnvase.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Envase Ingresado")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

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


        buttonAsoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coding, coden;
                coden = codEn;
                coding = codIn;

                if (!coden.equals("") && !coding.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "codin";
                            field[1] = "coden";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = coding;
                            data[1] = coden;
                            PutData putData = new PutData("https://pantryaidphp.000webhostapp.com/loginsign/signEnvaseTien.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Envase Asociado")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        /*Intent intent = new Intent(getApplicationContext(),ManageIngrediente.class);
                                        startActivity(intent);*/
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnEnvasesito:
                String nomben = parent.getItemAtPosition(position).toString();
                codEn = hmEnvase.get(nomben);
                Toast.makeText(this, codEn, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}