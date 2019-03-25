package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AnActivity extends NavDrawerActivity {
    private ImageView yearOne, yearTwo, yearThree, yearFour;
    private TextView nrMateriiAn1, nrMateriiAn2, nrMateriiAn3, nrMateriiAn4;
    private String an;
    private FirebaseFirestore db;
    private static final String TAG = "AnActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an);
        yearOne = findViewById(R.id.yearOne);
        yearTwo = findViewById(R.id.yearTwo);
        yearThree = findViewById(R.id.yearThree);
        yearFour = findViewById(R.id.yearFour);

        nrMateriiAn1 = findViewById(R.id.textview_materii_an1);
        nrMateriiAn2 = findViewById(R.id.textview_materii_an2);
        nrMateriiAn3 = findViewById(R.id.textview_materii_an3);
        nrMateriiAn4 = findViewById(R.id.textview_materii_an4);

        getMateriiNumber("an1", nrMateriiAn1);
        getMateriiNumber("an2", nrMateriiAn2);
        getMateriiNumber("an3", nrMateriiAn3);
        getMateriiNumber("an4", nrMateriiAn4);


        yearOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearOne();
            }
        });
        yearTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearTwo();
            }
        });
        yearThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chActivToYearThree();
            }
        });
        yearFour.setOnClickListener(new View.OnClickListener() {
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
        an = "an4";
        Intent intent = new Intent(AnActivity.this, MaterieActivity.class);
        intent.putExtra("an",an);
        startActivity(intent);

    }
    private void getMateriiNumber(String collectionPath, final TextView locationTextView){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collectionPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot =task.getResult();
                    int count = querySnapshot.size();
                    System.out.println(count);
                    String textviewAn  = Integer.toString(count) + " Materii";
                    locationTextView.setText(textviewAn);
                }else{
                    Log.d(TAG,"Error getting documents " + task.getException());
                }
            }
        });
    }
}
