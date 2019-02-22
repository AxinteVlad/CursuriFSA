package com.axintevlad.cursurifsa.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    Map<String,Object> user = new HashMap<>();
    private FirebaseAuth mAuth;
    private Button signup;
    private TextView emailEditText;
    private TextView passwordEditText;
    private ProgressDialog loadingBar;
    private Switch userType;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.button_signup);
        emailEditText = findViewById(R.id.text_email);
        passwordEditText = findViewById(R.id.text_password);
        userType = findViewById(R.id.switch_usertype);
        loadingBar = new ProgressDialog(this);

//        if(mAuth.getCurrentUser() != null){
//            logIn();
//        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crateNewAccount();
            }
        });
    }

    private void crateNewAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Nu ai introdus un email",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Nu ai introdus o parola",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Se creaza contul");
            loadingBar.setMessage("Asteptati");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            if(userType.isChecked()) {
                user.put("email",email);
                user.put("tip","profesor");
            }else{
                user.put("email",email);
                user.put("tip","elev");
            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Inregistrat", Toast.LENGTH_SHORT).show();
                        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        user.put("userUID",userUID);
                        db.collection("useri").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                        loadingBar.dismiss();
                        logIn();

                    }else
                        {
                        String message = task.getException().getMessage();
                        Toast.makeText(SignUpActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        }
                }
            });
        }
    }

    private void logIn(){
        //trece la mainactivity
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
