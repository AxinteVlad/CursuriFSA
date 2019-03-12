package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SaveMaterieActivity extends AppCompatActivity {

    private EditText editTextNume,editTextDescriere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_materie);


        setTitle("Adauga Materie");

        editTextNume = findViewById(R.id.edittext_nume);
        editTextDescriere = findViewById(R.id.edittext_descriere);

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

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("an1");
        notebookRef.add(new Materie(title, description));
        Toast.makeText(this, "Materie adaugata", Toast.LENGTH_SHORT).show();
        finish();
    }
}
