package com.example.pantrya.dto;

public class Receta {
    private String id;
    private String  nombreRe;
    private String  nombreAu;

    public Receta(String id, String nombreRe, String nombreAu) {
        this.id = id;
        this.nombreRe = nombreRe;
        this.nombreAu = nombreAu;
    }

    public Receta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreRe() {
        return nombreRe;
    }

    public void setNombreRe(String nombreRe) {
        this.nombreRe = nombreRe;
    }

    public String getNombreAu() {
        return nombreAu;
    }

    public void setNombreAu(String nombreAu) {
        this.nombreAu = nombreAu;
    }


}
