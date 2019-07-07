package com.gustavohalm.fisio.Models;

public class Patient {
    private int id;
    private String name;
    private String email;
    private String height;
    private String weight;
    private String celphone;
    private String addres_1;
    private String addres_2;
    private String addres_3;
    private String city;
    private String state;
    private int agreement;
    private String cpf;
    private String rg;
    private String born;
    private String description;
    private int fisio;


    public Patient(String name, String height, String weight, String cpf, String email, String born, String rg, String addres_1, String addres_2, String addres_3, String city, String state, int agreement, String description, String celphone) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.cpf = cpf;
        this.email = email;
        this.born = born;
        this.rg = rg;
        this.addres_1 = addres_1;
        this.addres_2 = addres_2;
        this.addres_3 = addres_3;
        this.city = city;
        this.state = state;
        this.agreement = agreement;
        this.description = description;
        this.celphone = celphone;
    }
    public Patient(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Patient(int id, String name, String email, String height, String weight, String celphone, String addres_1, String addres_2, String addres_3, String city, String state, int agreement, String cpf, String rg, String born, String description, int fisio) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.celphone = celphone;
        this.addres_1 = addres_1;
        this.addres_2 = addres_2;
        this.addres_3 = addres_3;
        this.city = city;
        this.state = state;
        this.agreement = agreement;
        this.cpf = cpf;
        this.rg = rg;
        this.born = born;
        this.description = description;
        this.fisio = fisio;
    }
    public Patient(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getAddres_1() {
        return addres_1;
    }

    public void setAddres_1(String addres_1) {
        this.addres_1 = addres_1;
    }

    public String getAddres_2() {
        return addres_2;
    }

    public void setAddres_2(String addres_2) {
        this.addres_2 = addres_2;
    }

    public String getAddres_3() {
        return addres_3;
    }

    public void setAddres_3(String addres_3) {
        this.addres_3 = addres_3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAgreement() {
        return agreement;
    }

    public void setAgreement(int agreement) {
        this.agreement = agreement;
    }

    public int getFisio() {
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }
}
