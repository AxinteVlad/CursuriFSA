package com.axintevlad.cursurifsa.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.adapters.SubscriptiiAdapter;
import com.axintevlad.cursurifsa.adapters.UserAdapter;
import com.axintevlad.cursurifsa.models.Subscription;
import com.axintevlad.cursurifsa.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AdminActivity extends AppCompatActivity {
    private UserAdapter.OnItemClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth user = FirebaseAuth.getInstance();
    private UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CollectionReference useriRef = db.collection("useri");
        Query query = useriRef.orderBy("email",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query,User.class)
                .build();

        adapter = new UserAdapter(options,AdminActivity.this);

        RecyclerView recyclerView = findViewById(R.id.admin_recylcleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
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
