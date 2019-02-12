package com.example.dany.icar;

/**
 * Created by Dany on 21.05.2018.
 */

public class Masina {

    String denumire;
    String tipCombustibil;
    String motorizare;
    String detaliiRezervare;
    int imagine;
    float pret;


    public Masina(String denumire, String tipCombustibil, String motorizare, String detaliiRezervare, int imagine, float pret) {
        this.denumire = denumire;
        this.tipCombustibil = tipCombustibil;
        this.motorizare = motorizare;
        this.detaliiRezervare = detaliiRezervare;
        this.imagine = imagine;
        this.pret=pret;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getTipCombustibil() {
        return tipCombustibil;
    }

    public void setTipCombustibil(String tipCombustibil) {
        this.tipCombustibil = tipCombustibil;
    }

    public String getMotorizare() {
        return motorizare;
    }

    public void setMotorizare(String motorizare) {
        this.motorizare = motorizare;
    }

    public String getDetaliiRezervare() {
        return detaliiRezervare;
    }

    public void setDetaliiRezervare(String detaliiRezervare) {
        this.detaliiRezervare = detaliiRezervare;
    }

    public int getImagine() {
        return imagine;
    }

    public void setImagine(int imagine) {
        this.imagine = imagine;
    }
}
