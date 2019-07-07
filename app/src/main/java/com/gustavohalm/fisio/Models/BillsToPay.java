package com.gustavohalm.fisio.Models;

public class BillsToPay {
    private Double value;
    private String date;
    private String description;
    private int fisio;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFisio(){
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
