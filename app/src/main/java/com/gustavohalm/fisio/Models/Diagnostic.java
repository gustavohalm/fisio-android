package com.gustavohalm.fisio.Models;
/*
* fields = ['id', 'name','appointment', 'patient', 'fisio', 'anamnese','evolution','trainning', 'physic_exam','scale','observation']
   read_only_fields = ('id',  'fisio')
*
*
* */

public class Diagnostic{
    private int appointment;
    private String name;
    private int patient;
    private int fisio;
    private String anamnese;
    private String evolution;
    private String taining;
    private String physic_exam;
    private String scale;
    private String observation;

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getTaining() {
        return taining;
    }

    public void setTaining(String taining) {
        this.taining = taining;
    }

    public String getPhysic_exam() {
        return physic_exam;
    }

    public void setPhysic_exam(String physic_exam) {
        this.physic_exam = physic_exam;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getFisio(){
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }
    public int getAppointment() {
        return appointment;
    }

    public void setAppointment(int appointment) {
        this.appointment = appointment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }
}
