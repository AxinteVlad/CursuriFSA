package com.axintevlad.cursurifsa.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.MaterieAdapter;
import com.axintevlad.cursurifsa.models.Materie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YearOneActivity extends NavDrawerActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;

    private List<Materie> materieList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_one);

        FloatingActionButton fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.yearOne_recylcleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        materieList = new ArrayList<>();
//        for(int i = 0;i<10;i++){
//            Materie materie = new Materie("nume" + i+1,"Lore dummy data");
//            materieList.add(materie);
//        }
        adapter = new MaterieAdapter(materieList,this);

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        db.collection("an1").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        progressBar.setVisibility(View.GONE);

                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d: list){
                                Materie materie = d.toObject(Materie.class);
                                materieList.add(materie);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity or fragment
            }
        });
    }

    @Override
    protected int getNavigationItemID() {
        return R.id.yearOne_recylcleView;
    }
}
