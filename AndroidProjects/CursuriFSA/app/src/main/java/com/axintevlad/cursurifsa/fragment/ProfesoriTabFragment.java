package com.axintevlad.cursurifsa.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.UserAdapter;
import com.axintevlad.cursurifsa.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class ProfesoriTabFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth user = FirebaseAuth.getInstance();
    private UserAdapter adapter;

    public ProfesoriTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profesori_tab, container, false);

        CollectionReference useriRef = db.collection("useri");
        Query query = useriRef.orderBy("email", Query.Direction.ASCENDING).whereEqualTo("tip", "profesor");
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();

        adapter = new UserAdapter(options, getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.recylcleview_profesoritab);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return view;
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
}