package com.axintevlad.cursurifsa.models;

/**
 * Created by vlad__000 on 25-Feb-19.
 */
public class User {
    private static User unqueInstance;

    private String email;
    private String tip;
    private String userUID;

    private User(){}

    public static User getInstance() {
        if(unqueInstance == null){
            unqueInstance = new User();
        }
        return unqueInstance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }


}
