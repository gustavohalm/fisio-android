package com.gustavohalm.fisio.Models;

public class ImageDiagnostic {
    private int diagnostic;
    private String url;
    private String description;
    private int fisio;

    public int getFisio(){
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }
    public int getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(int diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
