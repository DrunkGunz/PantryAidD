package com.example.pantrya.dal;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantrya.R;
import com.example.pantrya.dto.Despensa;

import java.util.List;

public class DespensaAdapt extends ArrayAdapter<Despensa> {
    private  List<Despensa> despensaList;
    private Bitmap bitmap;
    private Context mCtx;

    public DespensaAdapt( List<Despensa> despensaList, Context mCtx) {
        super(mCtx, R.layout.list_item, despensaList);
        this.despensaList = despensaList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);

        //getting text views
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewCantidad = listViewItem.findViewById(R.id.textViewCantidad);
        TextView textViewEnvaseActual = listViewItem.findViewById(R.id.textViewEnvaseActual);

        //Getting the hero for the specified position
        Despensa desp = despensaList.get(position);

        //setting hero values to textviews
        textViewName.setText(desp.getIngrediente());
        textViewCantidad.setText(desp.getCantidad());
        textViewEnvaseActual.setText(desp.getGramo());

        //returning the listitem
        return listViewItem;
    }

    public DespensaAdapt(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public DespensaAdapt(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DespensaAdapt(@NonNull Context context, int resource, @NonNull Despensa[] objects) {
        super(context, resource, objects);
    }

    public DespensaAdapt(@NonNull Context context, int resource, int textViewResourceId, @NonNull Despensa[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DespensaAdapt(@NonNull Context context, int resource, @NonNull List<Despensa> objects) {
        super(context, resource, objects);
    }

    public DespensaAdapt(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Despensa> objects) {
        super(context, resource, textViewResourceId, objects);
    }


}

