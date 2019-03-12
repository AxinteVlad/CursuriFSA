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

public class SaveMaterieActivity extends AppCompatActivity {

    private EditText editTextNume,editTextDescriere;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_materie);

        db = FirebaseFirestore.getInstance();

        editTextNume = findViewById(R.id.edittext_nume);
        editTextDescriere = findViewById(R.id.edittext_descriere);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nume = editTextNume.getText().toString().trim();
                String descriere = editTextDescriere.getText().toString().trim();

                if(!validateInputs(nume,descriere)){
                    CollectionReference dbMaterii = db.collection("an1");

                    Materie materie = new Materie(nume,descriere);

                    dbMaterii.add(materie)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    String docID = documentReference.getId();
                                    String denumire,link;
                                    denumire = "";
                                    link ="";
                                    Curs curs = new Curs(denumire,link);
                                    CollectionReference dbMaterii = db.collection("an1");
                                    dbMaterii.document(docID).collection("cursuri").add(curs);

                                    Toast.makeText(SaveMaterieActivity.this, "Materie adaugata!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SaveMaterieActivity.this, YearOneActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SaveMaterieActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });







                }
            }
        });

    }
    private boolean validateInputs(String nume, String descriere) {
        if (nume.isEmpty()) {
            editTextNume.setError("Name required");
            editTextNume.requestFocus();
            return true;
        }

        if (descriere.isEmpty()) {
            editTextDescriere.setError("Brand required");
            editTextDescriere.requestFocus();
            return true;
        }


        return false;
    }
}
