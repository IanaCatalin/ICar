package com.example.dany.icar;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dany on 17.06.2018.
 */
@IgnoreExtraProperties
public class Utilizator {

    String nume;
    String prenume;
    int varsta;
    String nrTelefon;
    String cnp;

    public Utilizator() {
        nume = "";
        prenume = "";
        nrTelefon = "";
        cnp = "";
    }

    public Utilizator(String nume, String prenume, int varsta, String nrTelefon, String cnp) {
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.nrTelefon = nrTelefon;
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getcnp() {
        return cnp;
    }

    public void setcnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", varsta=" + varsta +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }

    @Exclude

    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("nume", nume);

        result.put("prenume", prenume);

        result.put("varsta", varsta);

        result.put("nrTelefon", nrTelefon);

        result.put("cnp", cnp);

        return result;

    }

}
