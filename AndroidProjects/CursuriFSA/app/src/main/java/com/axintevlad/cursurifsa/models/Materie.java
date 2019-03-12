package com.axintevlad.cursurifsa.models;

import java.util.ArrayList;

/**
 * Created by vlad__000 on 04-Mar-19.
 */
public class Materie {
    private String nume;
    private String descriere;
//    private ArrayList<Curs> listaCurs;
//    private ArrayList<Teme> listaTeme;
//    private ArrayList<Resurse> listaResurse;

    public Materie() { }
    public Materie(String nume, String descriere) {
        this.nume = nume;
        this.descriere = descriere;
    }

//    public ArrayList<Curs> getListaCurs() {
//        return listaCurs;
//    }
//
//    public void setListaCurs(ArrayList<Curs> listaCurs) {
//        this.listaCurs = listaCurs;
//    }
//
//    public ArrayList<Teme> getListaTeme() {
//        return listaTeme;
//    }
//
//    public void setListaTeme(ArrayList<Teme> listaTeme) {
//        this.listaTeme = listaTeme;
//    }
//
//    public ArrayList<Resurse> getListaResurse() {
//        return listaResurse;
//    }
//
//    public void setListaResurse(ArrayList<Resurse> listaResurse) {
//        this.listaResurse = listaResurse;
//    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
