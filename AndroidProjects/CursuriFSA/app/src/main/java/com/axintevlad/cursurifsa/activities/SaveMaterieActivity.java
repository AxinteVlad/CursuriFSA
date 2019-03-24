package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class SaveMaterieActivity extends AppCompatActivity {

    private EditText editTextNume,editTextDescriere;
    private ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
    private String imageUrl;
    private final String urlImg1 = "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader1.png?alt=media&token=ab12d3de-7b5f-4383-adc5-81eba83c94f1";
    private final String urlImg2 = "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader2.png?alt=media&token=de5b026e-ec75-462d-ba25-5758a389beda";
    private final String urlImg3 = "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader3.png?alt=media&token=16c65b02-89a0-4f65-a8b9-f684dd4629a2";
    private final String urlImg4 = "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader4.png?alt=media&token=e559e491-bd6f-47e8-b881-a78e2fcd3ec5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_materie);


        setTitle("Adauga Materie");

        editTextNume = findViewById(R.id.edittext_nume);
        editTextDescriere = findViewById(R.id.edittext_descriere);
        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        Picasso.get().load(urlImg1).fit().into(imageButton1);
        Picasso.get().load(urlImg2).fit().into(imageButton2);
        Picasso.get().load(urlImg3).fit().into(imageButton3);
        Picasso.get().load(urlImg4).fit().into(imageButton4);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               imageUrl = urlImg1;
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUrl = urlImg2;
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(1000);
                imageButton2.setAnimation(animation);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUrl = urlImg3;
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUrl = urlImg3;
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageUrl = urlImg4;
            }
        });


        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMaterie();

            }
        });

    }
    private void saveMaterie() {
        String title = editTextNume.getText().toString();
        String description = editTextDescriere.getText().toString();



        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Introduceti un titlu si o descriere", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intentExtra = getIntent();
        String an = intentExtra.getStringExtra("an");
        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection(an);
        notebookRef.add(new Materie(title, description,imageUrl));
        Toast.makeText(this, "Materie adaugata", Toast.LENGTH_SHORT).show();
        finish();
    }
}
