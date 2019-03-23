package com.axintevlad.cursurifsa.fragment;


import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.axintevlad.cursurifsa.CursuriFsaApplication;
import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.activities.SaveTemaActivity;
import com.axintevlad.cursurifsa.adapters.CursAdapter;
import com.axintevlad.cursurifsa.adapters.TemeAdapter;
import com.axintevlad.cursurifsa.models.Teme;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemeFragment extends Fragment {


    private String id,an;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentSnapshot documentSnapshot;

    private TemeAdapter adapter;
    FloatingActionButton fab;


    public TemeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            an = bundle.getString("an",null);
            id = bundle.getString("ID", null);
        }


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView  = inflater.inflate(R.layout.fragment_teme, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_teme);
        CursuriFsaApplication.getInstance().checkElev(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cand dai click pe fab din cursuri
                //   de sters bundle
//                Bundle data = new Bundle();
//                data.putString("an",an);
//                data.putString("ID",id);

                Intent intent = new Intent(getActivity(), SaveTemaActivity.class);
                intent.putExtra("an",an);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });

        CollectionReference cursuriRef = db.collection(an).document(id).collection("teme");
        Query query = cursuriRef.orderBy("denumire",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Teme> options = new FirestoreRecyclerOptions.Builder<Teme>()
                .setQuery(query,Teme.class)
                .build();

        adapter = new TemeAdapter(options);

        RecyclerView recyclerView = rootView.findViewById(R.id.teme_recylcleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Teme teme = documentSnapshot.toObject(Teme.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                Toast.makeText(getActivity(), "Link "+ teme.getLink(), Toast.LENGTH_SHORT).show();

                Uri linkUri = Uri.parse(teme.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(linkUri,"application/pdf");
                startActivity(intent);
            }
        });


        // Inflate the layout for this fragment
        return rootView;

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
