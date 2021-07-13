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
import android.widget.Switch;
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

public class ManageReceta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText editTextRnomb, editTextDesc, editTextPrep;
    Button buttonCrearREC, buttonCancel;
    String usuario, codDF;
    Spinner spinnerDif;
    ArrayList<String> listaDificultad = new ArrayList<>();
    HashMap<String, String> hmDificultad = new HashMap<String, String>();
    ArrayAdapter<String> adapterDificultad;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_receta);

        editTextRnomb = findViewById(R.id.txtTreceta);
        editTextDesc = findViewById(R.id.txtDescripion);
        editTextPrep = findViewById(R.id.txtPrep);
        buttonCrearREC = findViewById(R.id.btnCrearREC);
        buttonCancel = findViewById(R.id.btnCancelar);

        requestQueue = Volley.newRequestQueue(this);

        spinnerDif = findViewById(R.id.spnDificultad);

        String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrDificultad.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("dificultad");
                    hmDificultad.clear();
                    for (int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cod_df = jsonObject.optString("COD_DF");
                        String nombreDif = jsonObject.optString("NOMBRE");
                        listaDificultad.add(nombreDif);
                        hmDificultad.put(nombreDif, cod_df);
                        adapterDificultad = new ArrayAdapter<>(ManageReceta.this, android.R.layout.simple_spinner_item, listaDificultad);
                        adapterDificultad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDif.setAdapter(adapterDificultad);

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
        spinnerDif.setOnItemSelectedListener(this);


        buttonCrearREC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, nombreREC, nombDesc, nombPrep;
                SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
                username = pref.getString("usuario","");

                nombreREC = String.valueOf(editTextRnomb.getText());
                nombDesc = String.valueOf(editTextDesc.getText());
                nombPrep = String.valueOf(editTextPrep.getText());

                if (!username.equals("") && !nombreREC.equals("") && !nombDesc.equals("") && !nombPrep.equals("") && !codDF.equals("")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[5];
                            field[0] = "username";
                            field[1] = "nombre";
                            field[2] = "descripcion";
                            field[3] = "prep";
                            field[4] = "codDF";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = username;
                            data[1] = nombreREC;
                            data[2] = nombDesc;
                            data[3] = nombPrep;
                            data[4] = codDF;
                            PutData putData = new PutData("https://pantryaidphp.000webhostapp.com/loginsign/signRec.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Receta Creada")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),RecetaIngrediente.class);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnDificultad:
                String nombDF = parent.getItemAtPosition(position).toString();
                codDF = hmDificultad.get(nombDF);
                Toast.makeText(this, codDF, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}