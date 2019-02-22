package com.axintevlad.cursurifsa.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProgressDialog loadingBar;

    private Button buttonLogin,buttonSignup;
    private TextView emailEditText,passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignup = findViewById(R.id.button_signup);
        buttonLogin = findViewById(R.id.button_login);
        emailEditText = findViewById(R.id.text_email);
        passwordEditText = findViewById(R.id.text_password);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        if(mAuth.getCurrentUser() != null){
            logIn();
        }

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });



    }

    private void logIn(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "Introdu un email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Introdu o parola", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Asteptati");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userUID = currentUser.getUid();
                        // Create a reference to the cities collection
                        CollectionReference citiesRef = db.collection("useri");

                        // Create a query against the collection.
                        Query query = citiesRef.whereEqualTo("userUID", userUID);

                        Toast.makeText(LoginActivity.this, "Te-ai logat!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        changeActivity();
                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Eroare + "+ message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void changeActivity(){
        //trece la mainactivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
