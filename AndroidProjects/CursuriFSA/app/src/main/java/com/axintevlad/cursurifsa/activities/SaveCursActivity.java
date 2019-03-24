package com.axintevlad.cursurifsa.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.CursuriFsaApplication;
import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.fragment.CursuriFragment;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.axintevlad.cursurifsa.models.Resurse;
import com.axintevlad.cursurifsa.models.Teme;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SaveCursActivity extends AppCompatActivity {
    private static final String TAG = "SaveCursActivity";
    private final static int PICK_PDF_CODE = 9;
    private final static int SELECT_PDF_CODE = 86;


    private EditText editTextNume,editTextLink;
    private Button alegeFisier,incarcaFisier,saveMaterie;
    private String an,id;
    private TextView textViewStatus;
    private int TIP_FRAGMENT;

    private Uri pdfUri;
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_curs);

        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextNume = findViewById(R.id.edittext_nume_curs);
        editTextLink = findViewById(R.id.edittext_link_curs);
        alegeFisier = findViewById(R.id.button_alegeFisier);
        incarcaFisier = findViewById(R.id.button_incarcaFisier);
        saveMaterie = findViewById(R.id.button_save_curs);
        progressBar = findViewById(R.id.progressbar_curs);
        textViewStatus = findViewById(R.id.textViewStatus);


        Intent intentExtra = getIntent();
        an = intentExtra.getStringExtra("an");
        id = intentExtra.getStringExtra("ID");
        TIP_FRAGMENT = intentExtra.getIntExtra("CODE_FRAGMENT",-1);
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

        alegeFisier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(SaveCursActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else{
                    ActivityCompat.requestPermissions(SaveCursActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PICK_PDF_CODE);
                }

            }
        });

        incarcaFisier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdfUri!=null){
                    salveazaPdf(pdfUri);
                }else{
                    Toast.makeText(SaveCursActivity.this,"Selecteaza un fisier!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void salveazaPdf(Uri pdfUri) {
        progressBar.setVisibility(View.VISIBLE);

        final StorageReference storageReference = storage.getReference();
        storageReference.child("Cursuri").child(id).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url = uri.getResult();
                        editTextLink.setText(url.toString());
                        Log.i("Download URL ", url.toString());

                        progressBar.setVisibility(View.GONE);
                        textViewStatus.setText("Fisierul a fost uploadat!");
                       Toast.makeText(SaveCursActivity.this,"Fisier Uploadat!",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SaveCursActivity.this,"NU S-A PUTUT UPLOADA",Toast.LENGTH_LONG).show();
            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double currentProgress = (double) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        textViewStatus.setText((int) currentProgress +"% Uploading");
                    }
                });





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PICK_PDF_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else
        {
            Toast.makeText(SaveCursActivity.this,"Acorda permisiunea",Toast.LENGTH_LONG).show();
        }
    }

    private void selectPdf() {
        //selecteaza fisier
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,SELECT_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //verificam daca a selectat un fisier
        if(requestCode == SELECT_PDF_CODE && resultCode == RESULT_OK && data!=null){
            pdfUri = data.getData();
        }else{
            Toast.makeText(SaveCursActivity.this,"Alege un fisier!",Toast.LENGTH_LONG).show();
        }
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
