package com.axintevlad.cursurifsa.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;


import java.util.ArrayList;

public class OrarActivity extends NavDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orar);


    }

    @Override
    protected int getNavigationItemID() {
        return 0;
    }

}
