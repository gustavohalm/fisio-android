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
    private String  hma;
    private String  hmp;
    private String  hmf;
    private String  qp;
    private String  medication;
    private String fowarding;
    private String functional;
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

    public String getHma() {
        return hma;
    }

    public void setHma(String hma) {
        this.hma = hma;
    }

    public String getHmp() {
        return hmp;
    }

    public void setHmp(String hmp) {
        this.hmp = hmp;
    }

    public String getHmf() {
        return hmf;
    }

    public void setHmf(String hmf) {
        this.hmf = hmf;
    }

    public String getQp() {
        return qp;
    }

    public void setQp(String qp) {
        this.qp = qp;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getFowarding() {
        return fowarding;
    }

    public void setFowarding(String fowarding) {
        this.fowarding = fowarding;
    }

    public String getFunctional() {
        return functional;
    }

    public void setFunctional(String functional) {
        this.functional = functional;
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
