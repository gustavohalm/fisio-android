package com.gustavohalm.fisio.Models;

public class AppointmentNew
{
    private int patient;
    private String day;
    private String time;
    private Double value;
    private int fisio;
    private int id;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

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
    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
