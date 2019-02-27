package com.axintevlad.cursurifsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.axintevlad.cursurifsa.activities.NavDrawerActivity;

public class MainActivity extends NavDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected int getNavigationItemID() {
        return R.id.nav_home;
    }
}
