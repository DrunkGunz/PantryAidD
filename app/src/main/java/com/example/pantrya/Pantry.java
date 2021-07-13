package com.example.pantrya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pantrya.dal.DespensaAdapt;
import com.example.pantrya.dto.Despensa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pantry extends AppCompatActivity {


    ListView listView;
    //DespensaDAL despensaDAL = new DespensaDAL();
    String usuario;
    Button btnLout, buttonMireceta;
    List<Despensa> despensaList;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        buttonMireceta = findViewById(R.id.btnMisRecetas);
        btnLout = findViewById(R.id.btnLogout);
        SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        usuario = pref.getString("usuario", "");
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrDesp.php?codU="+usuario;
        listView = findViewById(R.id.listViewPantry);
        despensaList = new ArrayList<>();
        llenarDesp(url);
        buttonMireceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MiReceta.class);
                startActivity(intent);
                finish();
            }
        });


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

        FloatingActionButton fabAddIng = (FloatingActionButton) findViewById(R.id.fabAddIng);
        fabAddIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageIngrediente.class);
                startActivity(intent);
                finish();
            }
        });





    }


    //no funciono
    /*private void loadDespList(String JSON_URL) {
        //getting the progressbar

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray despArray = obj.getJSONArray("despensa");
                            //now looping through all the elements of the json array
                            for (int i = 0; i < despArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject despObject = despArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object

                                String id;
                                String ingrediente;
                                String cantidad;
                                String stockmin;
                                String gramo;


                                id = despObject.optString("COD_DES");
                                ingrediente = despObject.optString("NOMBRE");
                                cantidad = despObject.optString("CANTIDAD");
                                stockmin = despObject.optString("STOCK_MINIMO");
                                gramo = despObject.optString("GRAMO");

                                Despensa despensa = new Despensa(id, ingrediente, cantidad, stockmin, gramo);
                                //adding the hero to herolist
                                despensaList.add(despensa);
                            }
                            //creating custom adapter object
                            DespensaAdapt adapter = new DespensaAdapt(despensaList, getApplicationContext());
                            //adding the adapter to listview
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }*/


   private void llenarDesp(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("despensa");
                    for (int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Despensa des = new  Despensa();

                        des.setId(jsonObject.optString("COD_DES"));
                        des.setIngrediente(jsonObject.optString("NOMBRE"));
                        des.setCantidad(jsonObject.optString("CANTIDAD"));
                        des.setStockmin(jsonObject.optString("STOCK_MINIMO"));
                        des.setGramo(jsonObject.optString("GRAMO"));
                        despensaList.add(des);
                    }
                    DespensaAdapt adapter = new DespensaAdapt(despensaList, getApplicationContext());
                    //adding the adapter to listview
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}