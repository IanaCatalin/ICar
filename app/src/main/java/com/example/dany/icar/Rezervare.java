package com.example.dany.icar;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dany on 29.05.2018.
 */
@IgnoreExtraProperties
public class Rezervare {
    String dataPredare;
    String dataPreluare;
    String email;
    String locatiePredare;
    String locatiePreluare;
    String masina;
    float pret;


    public Rezervare() {
    }

    public Rezervare(String dataPredare, String dataPreluare, String email, String locatiePredare, String locatiePreluare, String masina, float pret) {
        this.dataPredare = dataPredare;
        this.dataPreluare = dataPreluare;
        this.email = email;
        this.locatiePredare = locatiePredare;
        this.locatiePreluare = locatiePreluare;
        this.masina = masina;
        this.pret=pret;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public String getDataPredare() {
        return dataPredare;
    }

    public void setDataPredare(String dataPredare) {
        this.dataPredare = dataPredare;
    }

    public String getDataPreluare() {
        return dataPreluare;
    }

    public void setDataPreluare(String dataPreluare) {
        this.dataPreluare = dataPreluare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocatiePredare() {
        return locatiePredare;
    }

    public void setLocatiePredare(String locatiePredare) {
        this.locatiePredare = locatiePredare;
    }

    public String getLocatiePreluare() {
        return locatiePreluare;
    }

    public void setLocatiePreluare(String locatiePreluare) {
        this.locatiePreluare = locatiePreluare;
    }

    public String getMasina() {
        return masina;
    }

    public void setMasina(String masina) {
        this.masina = masina;
    }

    @Override
    public String toString() {
        return "Rezervarea cu datele:" + '\n' +
                " Data preluare: " + dataPreluare + '\n' +
                " Data predare: " + dataPredare + '\n' +
                " Email: " + email + '\n' +
                " Locatie predare: " + locatiePredare + '\n' +
                " Locatie preluare: " + locatiePreluare + '\n' +
                " Masina selectata: " + masina + '\n' +
                " Total de plata: " + pret;
    }


    @Exclude

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("dataPredare", dataPredare);

        result.put("dataPreluare", dataPreluare);

        result.put("email", email);

        result.put("locatiePredare", locatiePredare);

        result.put("locatiePreluare", locatiePreluare);

        result.put("masina", masina);

        result.put("pret",pret);



        return result;

    }
}
