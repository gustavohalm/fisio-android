package com.gustavohalm.fisio.Models;

public class Procedure {
    private String name;
    private Double value;
    private int fisio;

    public int getFisio(){
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Double getValue(){
        return value;
    }
    public void setValue(Double value){
        this.value = value;
    }
}
