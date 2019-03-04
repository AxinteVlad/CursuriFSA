package com.axintevlad.cursurifsa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.axintevlad.cursurifsa.R;

public class ProfesorActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
    }

    @Override
    protected int getNavigationItemID() {
        return R.id.nav_home;
    }


}
