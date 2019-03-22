package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.fragment.CursuriFragment;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SaveCursActivity extends AppCompatActivity {
    private static final String TAG = "SaveCursActivity";
    private EditText editTextNume,editTextLink;
    private Button alegeFisier,incarcaFisier,saveMaterie;
    private String an,id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_curs);


        editTextNume = findViewById(R.id.edittext_nume_curs);
        editTextLink = findViewById(R.id.edittext_link_curs);
        alegeFisier = findViewById(R.id.button_alegeFisier);
        incarcaFisier = findViewById(R.id.button_incarcaFisier);
        saveMaterie = findViewById(R.id.button_save_curs);



        Intent intentExtra = getIntent();
        an = intentExtra.getStringExtra("an");
        id = intentExtra.getStringExtra("ID");
        Log.d(TAG, "INTENTEXTRA: " + an + " " + id);



        saveMaterie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMaterie();
                Intent intent = new Intent(SaveCursActivity.this, BottomNavActivity.class);
                intent.putExtra("an",an);
                intent.putExtra("ID",id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveMaterie() {
        String title = editTextNume.getText().toString();
        String link = editTextLink.getText().toString();


        if (title.trim().isEmpty() || link.trim().isEmpty()) {
            Toast.makeText(this, "Introduceti un titlu si o descriere", Toast.LENGTH_SHORT).show();
            return;
        }


        CollectionReference collectionReference = FirebaseFirestore.getInstance()
                .collection(an).document(id).collection("cursuri");
        collectionReference.add(new Curs(title, link));
        Toast.makeText(this, "Curs adaugat", Toast.LENGTH_SHORT).show();
        finish();
    }


}
