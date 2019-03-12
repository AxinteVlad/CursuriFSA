package com.axintevlad.cursurifsa.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.CursAdapter;
import com.axintevlad.cursurifsa.adapters.MaterieAdapter;
import com.axintevlad.cursurifsa.models.Materie;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class YearOneActivity extends NavDrawerActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference materiiRef = db.collection("an1");
    private MaterieAdapter adapter;

    private ProgressBar progressBar;

    private List<Materie> materieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_one);

        FloatingActionButton fab = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressbar);

        setUpRecyclerView();

        //fab button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity or fragment
                Intent intent = new Intent(YearOneActivity.this, SaveMaterieActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setUpRecyclerView() {

        Query query = materiiRef.orderBy("titlu",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Materie> options = new FirestoreRecyclerOptions.Builder<Materie>()
                .setQuery(query,Materie.class)
                .build();

        adapter = new MaterieAdapter(options);
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = findViewById(R.id.yearOne_recylcleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MaterieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Materie materie = documentSnapshot.toObject(Materie.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(YearOneActivity.this,
                        "Position: " + position + " ID: " + id + "path: "+ path, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(YearOneActivity.this, BottomNavActivity.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });
    }
    @Override
    protected int getNavigationItemID() {
        return 0;
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
