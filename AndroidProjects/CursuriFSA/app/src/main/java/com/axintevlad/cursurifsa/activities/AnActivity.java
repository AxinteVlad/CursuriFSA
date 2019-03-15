package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.axintevlad.cursurifsa.R;

public class AnActivity extends NavDrawerActivity {
    private ImageView oneIc,twoIc,threeIc,fourIc;
    private String an;
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
        twoIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearTwo();
            }
        });
        threeIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearThree();
            }
        });
        fourIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearFour();
            }
        });
    }

    @Override
    protected int getNavigationItemID() {
        return R.id.nav_home;
    }

    private void chActivToYearOne(){
        //trece la mainactivity
        an = "an1";
        Intent intent = new Intent(AnActivity.this, MaterieActivity.class);
        intent.putExtra("an",an);
        startActivity(intent);

    }
    private void chActivToYearTwo(){
        //trece la mainactivity
        an = "an2";
        Intent intent = new Intent(AnActivity.this, MaterieActivity.class);
        intent.putExtra("an",an);
        startActivity(intent);

    }
    private void chActivToYearThree(){
        //trece la mainactivity
        an = "an3";
        Intent intent = new Intent(AnActivity.this, MaterieActivity.class);
        intent.putExtra("an",an);
        startActivity(intent);

    }
    private void chActivToYearFour(){
        //trece la mainactivity
        an = "an3";
        Intent intent = new Intent(AnActivity.this, MaterieActivity.class);
        intent.putExtra("an",an);
        startActivity(intent);

    }
}
