package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;
import com.google.common.reflect.ClassPath;

public class AnActivity extends NavDrawerActivity {
    private ImageView oneIc,twoIc,threeIc,fourIc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an);
        oneIc = findViewById(R.id.ic_one);
        twoIc = findViewById(R.id.ic_two);
        threeIc = findViewById(R.id.ic_three);
        fourIc = findViewById(R.id.ic_four);

        oneIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearOne();
            }
        });
    }

    @Override
    protected int getNavigationItemID() {
        return R.id.nav_home;
    }

    private void chActivToYearOne(){
        //trece la mainactivity
        Intent intent = new Intent(AnActivity.this, YearOneActivity.class);
        startActivity(intent);

    }
    private void chActivToYearTwo(){
        //trece la mainactivity
        Intent intent = new Intent(AnActivity.this, YearOneActivity.class);
        startActivity(intent);

    }
    private void chActivToYearThree(){
        //trece la mainactivity
        Intent intent = new Intent(AnActivity.this, YearOneActivity.class);
        startActivity(intent);

    }
    private void chActivToYearFour(){
        //trece la mainactivity
        Intent intent = new Intent(AnActivity.this, YearOneActivity.class);
        startActivity(intent);

    }
}
