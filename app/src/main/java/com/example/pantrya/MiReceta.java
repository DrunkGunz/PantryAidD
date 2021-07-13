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
import com.example.pantrya.dal.RecetaAdapt;
import com.example.pantrya.dto.Despensa;
import com.example.pantrya.dto.Receta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MiReceta extends AppCompatActivity {

    ListView listView;
    String usuario;
    List<Receta> recetaList;
    Button buttonDesp;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_receta);
        buttonDesp = findViewById(R.id.btnDespensa);

        requestQueue = Volley.newRequestQueue(this);
        SharedPreferences pref = getSharedPreferences("prefLogin", Context.MODE_PRIVATE);
        usuario = pref.getString("usuario", "");
        listView = findViewById(R.id.listViewReceta);
        recetaList = new ArrayList<>();
        String url = "https://pantryaidphp.000webhostapp.com/loginsign/obtnrRec.php?codU="+usuario;
        llenarMiRec(url);










        FloatingActionButton fabAddrec = (FloatingActionButton) findViewById(R.id.fabAddrec);
        fabAddrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageReceta.class);
                startActivity(intent);
                finish();
            }
        });

        buttonDesp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pantry.class);
                startActivity(intent);
                finish();
            }
        });




    }

    private void llenarMiRec(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("receta");
                    for (int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Receta res = new Receta();

                        res.setId(jsonObject.optString("COD_REC"));
                        res.setNombreRe(jsonObject.optString("NOMBRE"));
                        res.setNombreAu(jsonObject.optString("AUTOR"));
                        recetaList.add(res);
                    }

                    RecetaAdapt adapter = new RecetaAdapt(recetaList,getApplicationContext());
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