package com.example.pantrya.dto;

public class Despensa {
    private String id;
    private String ingrediente;
    private String cantidad;
    private String stockmin;
    private String gramo;

    public Despensa(String id, String ingrediente, String cantidad, String stockmin, String gramo) {
        this.id = id;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.stockmin = stockmin;
        this.gramo = gramo;
    }

    public Despensa() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getStockmin() {
        return stockmin;
    }

    public void setStockmin(String stockmin) {
        this.stockmin = stockmin;
    }

    public String getGramo() {
        return gramo;
    }

    public void setGramo(String gramo) {
        this.gramo = gramo;
    }
}
