package com.axintevlad.cursurifsa.models;

/**
 * Created by vlad__000 on 11.03.2019.
 */
public class Curs {
    private String denumire;
    private String link;

    public Curs() {
    }

    public Curs(String denumire, String link) {
        this.denumire = denumire;
        this.link = link;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
