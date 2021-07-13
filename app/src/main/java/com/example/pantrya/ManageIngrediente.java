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
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageIngrediente extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText editTextcantidadIn, editTextstockMin;
    Button buttonAddnuevoE, buttonIngrediente, buttonOK;
    String codIn, codTien;
    Spinner spinnerIng, spinnerEnvase;
    ArrayList<String> listaIngredient = new ArrayList<>();
    ArrayList<String> listaEnvase = new ArrayList<>();
    HashMap<String ,String> hmIngrediente = new HashMap<String,String>();
    HashMap<String ,String>  hmEnvase = new HashMap<String,String>();

    ArrayAdapter<String> adapterIngrediente;
    ArrayAdapter<String> adapterEnvase;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_ingrediente);
        editTextcantidadIn = findViewById(R.id.cantidadIn);
        editTextstockMin = findViewById(R.id.stockMin);
        buttonAddnuevoE = findViewById(R.id.btnAddenv);
        buttonIngrediente = findViewById(R.id.btnIngrediente);
        buttonOK = findViewById(R.id.btnListo);

        requestQueue = Volley.newRequestQueue(this);
        spinnerIng = findViewById(R.id.spnIngrediente);
        spinnerEnvase = findViewById(R.id.spnEnvase);

        String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrIngredientes.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("ingrediente");
                    hmIngrediente.clear();
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cod_IN = jsonObject.optString("COD_IN");
                        String nombreIngrediente = jsonObject.optString("NOMBRE");
                        listaIngredient.add(nombreIngrediente);
                        hmIngrediente.put(nombreIngrediente, cod_IN);
                        adapterIngrediente = new ArrayAdapter<>(ManageIngrediente.this, android.R.layout.simple_spinner_item, listaIngredient);
                        adapterIngrediente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerIng.setAdapter(adapterIngrediente);

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
        spinnerIng.setOnItemSelectedListener(this);

        buttonIngrediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

                String username, stock, stockmin;
                username = pref.getString("usuario","");
                stock = String.valueOf(editTextcantidadIn.getText());
                stockmin = String.valueOf(editTextstockMin.getText());

                if (!username.equals("") && !stock.equals("") && !stockmin.equals("") && !codTien.equals("") ) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "cantidad";
                            field[2] = "stockmin";
                            field[3] = "cod_tien";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = stock;
                            data[2] = stockmin;
                            data[3] = codTien;
                            PutData putData = new PutData("https://pantryaidphp.000webhostapp.com/loginsign/signDesp.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Ingrediente añadido")){
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

        buttonAddnuevoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addEnvase.class);
                intent.putExtra("codIN",codIn);
                startActivity(intent);
                //finish();
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Pantry.class);
                startActivity(intent);
                finish();
            }
        });






    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch(adapterView.getId()){
            case R.id.spnIngrediente:
                listaEnvase.clear();
                String selectdIng = adapterView.getSelectedItem().toString();
                codIn = hmIngrediente.get(selectdIng);
                String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrEnvase.php?selectIngrediente="+selectdIng;
                requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("envase");
                            hmEnvase.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String cod_tien = jsonObject.optString("COD_TIEN");
                                String nombreTien = jsonObject.optString("NOMBRE");
                                listaEnvase.add(nombreTien);
                                hmEnvase.put(nombreTien, cod_tien);
                                adapterEnvase = new ArrayAdapter<>(ManageIngrediente.this, android.R.layout.simple_spinner_item, listaEnvase);
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
                requestQueue.add(jsonObjectRequest);
                spinnerEnvase.setOnItemSelectedListener(this);
                break;

            case  R.id.spnEnvase:
                String nombTien = adapterView.getItemAtPosition(position).toString();
                codTien = hmEnvase.get(nombTien);
                Toast.makeText(this, codTien, Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}