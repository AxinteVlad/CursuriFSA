package com.axintevlad.cursurifsa.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.activities.SaveMaterieActivity;
import com.axintevlad.cursurifsa.activities.YearOneActivity;
import com.axintevlad.cursurifsa.adapters.CursAdapter;
import com.axintevlad.cursurifsa.adapters.MaterieAdapter;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class CursuriFragment extends Fragment {
    String id;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CursAdapter adapter;
    FloatingActionButton fab;


    public CursuriFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("ID", null);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.fragment_cursuri, container, false);
       // fab = (FloatingActionButton) view.findViewById(R.id.fab_cursuri);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //cand dai click pe fab din cursuri
//            }
//        });

        CollectionReference cursuriRef = db.collection("an1").document(id).collection("cursuri");
        Query query = cursuriRef.orderBy("denumire",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Curs> options = new FirestoreRecyclerOptions.Builder<Curs>()
                .setQuery(query,Curs.class)
                .build();

        adapter = new CursAdapter(options);

        RecyclerView recyclerView = rootView.findViewById(R.id.cursuri_recylcleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cursuri, container, false);

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
