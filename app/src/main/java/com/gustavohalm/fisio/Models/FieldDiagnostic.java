package com.gustavohalm.fisio.Models;

public class FieldDiagnostic {
    private int procedure;
    private int diagnostic;
    private int id;
    private String text;


    public int getProcedure() {
        return procedure;
    }

    public void setProcedure(int procedure) {
        this.procedure = procedure;
    }

    public int getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(int diagnostic) {
        this.diagnostic = diagnostic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
