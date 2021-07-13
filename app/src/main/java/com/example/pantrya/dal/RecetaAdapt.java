package com.example.pantrya.dal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pantrya.R;
import com.example.pantrya.dto.Despensa;
import com.example.pantrya.dto.Receta;

import java.util.List;

public class RecetaAdapt extends ArrayAdapter<Receta> {
    private List<Receta> recetaList;
    private Context mCtx;

    public RecetaAdapt( List<Receta> recetaList, Context mCtx) {
        super(mCtx, R.layout.list_receta ,recetaList );
        this.recetaList = recetaList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_receta, null, true);

        //getting text views
        TextView textViewReceta = listViewItem.findViewById(R.id.textViewNombreRE);
        TextView textViewNombreAu = listViewItem.findViewById(R.id.textViewNombreCre);

        //Getting the hero for the specified position
        Receta receta = recetaList.get(position);

        //setting hero values to textviews

        textViewReceta.setText("Receta: "+receta.getNombreRe());
        textViewNombreAu.setText("Autor: "+receta.getNombreAu());

        //returning the listitem
        return listViewItem;
    }



}
