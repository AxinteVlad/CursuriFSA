package com.axintevlad.cursurifsa.models;

/**
 * Created by vlad__000 on 09.04.2019.
 */
public class Subscription {
    private String subscribeId;
    private boolean status;
    private String nume;

    public String getNume() {
        return nume;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public boolean isStatus() {
        return status;
    }
}
