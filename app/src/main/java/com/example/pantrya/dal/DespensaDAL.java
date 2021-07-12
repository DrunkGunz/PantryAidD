package com.example.pantrya.dal;

import com.example.pantrya.dto.Despensa;

import java.util.ArrayList;
import java.util.List;

public class DespensaDAL {
    private List<Despensa> despensas = new ArrayList<Despensa>();

    public void addDesp(Despensa des){
        despensas.add(des);
    }

    public List<Despensa> getALL(){
        return despensas;
    }

    public Despensa findbyID(String codDes){

        for (Despensa d : despensas){
            if (d.getId().equals(codDes)){
                return d;
            }
        }
        return null;
    }

    public List<Despensa> getDespensas() {
        return despensas;
    }

    public void setDespensas(List<Despensa> despensas) {
        this.despensas = despensas;
    }

    public boolean deleteDes(Despensa d){
        return despensas.remove(d);
    }

}
