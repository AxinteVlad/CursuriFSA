package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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
import com.axintevlad.cursurifsa.adapters.ViewPagerAdapter;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import android.support.v7.widget.Toolbar;
import java.util.Objects;

public class SaveMaterieActivity extends AppCompatActivity {

    private EditText editTextNume,editTextDescriere;
    private String imageUrl;

    private String[] imageUrls = new String[]{
            "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader1.png?alt=media&token=ab12d3de-7b5f-4383-adc5-81eba83c94f1",
            "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader2.png?alt=media&token=de5b026e-ec75-462d-ba25-5758a389beda",
            "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader3.png?alt=media&token=16c65b02-89a0-4f65-a8b9-f684dd4629a2",
            "https://firebasestorage.googleapis.com/v0/b/cursurifsa.appspot.com/o/Header_materii%2Fmaterie_hader4.png?alt=media&token=e559e491-bd6f-47e8-b881-a78e2fcd3ec5",
    };
    private ViewPager viewPager;
    private Toolbar mToolbar;
    private WormDotsIndicator wormDotsIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_materie);
        editTextNume = findViewById(R.id.edittext_nume);
        editTextDescriere = findViewById(R.id.edittext_descriere);
        wormDotsIndicator = findViewById(R.id.dots_indicator);

        //tooldbar
        mToolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Inapoi");
        mToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_arrow);

        //view pager
        viewPager = findViewById(R.id.view_pager_materie);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);
        //dots
        wormDotsIndicator.setViewPager(viewPager);
        viewPager.getCurrentItem();


        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput(view);
            }
        });

    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean validateTitlu() {
        String titluInput = editTextNume.getText().toString().trim();

        if (titluInput.isEmpty()) {
            editTextNume.setError("Field can't be empty");
            return false;
        } else {
            editTextNume.setError(null);
            return true;
        }
    }

    private boolean validateDescriere() {
        String descriereInput = editTextDescriere.getText().toString().trim();

        if (descriereInput.isEmpty()) {
            editTextDescriere.setError("Field can't be empty");
            return false;
        } else {
            editTextDescriere.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateTitlu() || !validateDescriere()) {
            return;
        }else {
            saveMaterie();
        }
    }


    private void saveMaterie() {
        String title = editTextNume.getText().toString();
        String description = editTextDescriere.getText().toString();

        //adaugare imagine de pe pozitia respectiva din viewpager
        int i =  viewPager.getCurrentItem();
        for(int k = 0;k<imageUrls.length;k++){
            if(i == k)
                imageUrl = imageUrls[k];
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
