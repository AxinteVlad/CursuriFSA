package com.axintevlad.cursurifsa.models;

import java.util.ArrayList;

/**
 * Created by vlad__000 on 04-Mar-19.
 */
public class Materie {
    private String titlu;
    private String descriere;
    private String imageUrl;
    private String materieUid;


    public Materie() { }
    public Materie(String nume, String descriere) {
        this.titlu = nume;
        this.descriere = descriere;
    }
    public Materie(String nume, String descriere,String imageUrl) {
        this.titlu = nume;
        this.descriere = descriere;
        this.imageUrl = imageUrl;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setNume(String nume) {
        this.titlu = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMaterieUid() {
        return materieUid;
    }
}
