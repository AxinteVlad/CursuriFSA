package com.axintevlad.cursurifsa.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.CursAdapter;
import com.axintevlad.cursurifsa.adapters.SubscriptiiAdapter;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Subscription;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SubscriptiiFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentSnapshot documentSnapshot;
    private FirebaseAuth user = FirebaseAuth.getInstance();
     SubscriptiiAdapter adapter;

    public SubscriptiiFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptii, container, false);

        CollectionReference cursuriRef = db.collection("useri").document(user.getUid()).collection("subs");
        Query query = cursuriRef.whereEqualTo("status",true);
        FirestoreRecyclerOptions<Subscription> options = new FirestoreRecyclerOptions.Builder<Subscription>()
                .setQuery(query,Subscription.class)
                .build();

        adapter = new SubscriptiiAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.subscriptii_recylcleView);
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
