package com.axintevlad.cursurifsa.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class InformatiiFragment extends Fragment {
    EditText editText_facultate,editText_an,editText_telefon;
    TextView edit_facultate,edit_an,edit_telefon;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth user = FirebaseAuth.getInstance();
    private static final String TAG = "InformatiiFragment";
    public InformatiiFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informatii, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText_facultate = getView().findViewById(R.id.edittext_facultate);
        editText_an = getView().findViewById(R.id.edittext_an);
        editText_telefon = getView().findViewById(R.id.edittext_telefon);

        edit_facultate = getView().findViewById(R.id.textView_edit_facultate);
        edit_an = getView().findViewById(R.id.textView_edit_an);
        edit_telefon = getView().findViewById(R.id.textView_edit_telefon);


        DocumentReference docRef = db.collection("useri").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        Log.i(TAG, "onComplete: " + doc.getData());
                        editText_facultate.setText(doc.getString("facultate"));
                        editText_an.setText(doc.getString("an"));
                        editText_telefon.setText(doc.getString("telefon"));

                    }
                }
            }
        });

        edit_facultate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("facultate",editText_facultate.getText().toString());
                db.collection("useri").document(user.getUid()).set(data, SetOptions.merge());
                Toast.makeText(getActivity(),"Faculate adaugata!",Toast.LENGTH_SHORT).show();
            }
        });

        edit_an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("an",editText_an.getText().toString());
                db.collection("useri").document(user.getUid()).set(data, SetOptions.merge());
                Toast.makeText(getActivity(),"An adaugat!",Toast.LENGTH_SHORT).show();
            }
        });

        edit_telefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("telefon",editText_an.getText().toString());
                db.collection("useri").document(user.getUid()).set(data, SetOptions.merge());
                Toast.makeText(getActivity(),"Telefon adaugat!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}