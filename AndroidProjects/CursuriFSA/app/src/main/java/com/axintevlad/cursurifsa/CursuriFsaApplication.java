package com.axintevlad.cursurifsa;

/**
 * Created by vlad__000 on 22.03.2019.
 */
public class CursuriFsaApplication {
    public  int CURS_FRAGMENT_CODE =11;
    public final static int TEME_FRAGMENT_CODE =22;
    public final static int RESURSE_FRAGMENT_CODE =33;

    private static CursuriFsaApplication instance = null;

    private CursuriFsaApplication (){

    }
    public static CursuriFsaApplication getInstance(){
        if(instance == null){
            instance = new CursuriFsaApplication();
        }
        return instance;
    }

}
