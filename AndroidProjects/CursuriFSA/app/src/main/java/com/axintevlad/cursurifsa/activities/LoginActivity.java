package com.axintevlad.cursurifsa.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = LoginActivity.class.getSimpleName();
    private static String tip_admin = "admin";
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private Button buttonLogin;
    private TextView emailEditText,passwordEditText,textViewSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewSignUp = findViewById(R.id.textView_signup);
        buttonLogin = findViewById(R.id.button_login);
        emailEditText = findViewById(R.id.text_email);
        passwordEditText = findViewById(R.id.text_password);
        mAuth = FirebaseAuth.getInstance();
        checkUserLoggedIn();

        if(mAuth.getCurrentUser() != null){
            logIn();
        }


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
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
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        final AlertDialog dialog = builder.create();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this, "Introdu un email", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Introdu o parola", Toast.LENGTH_SHORT).show();
        }else{

            dialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        final DocumentReference docRef = db.collection("useri").document(userUID);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        User user = User.getInstance();
                                        user = document.toObject(User.class);
                                        Log.d(TAG, "DocumentSnapshot data: " + document.getData()+"///////////" + user.getTip());
                                        if(user.getTip().equals(tip_admin)){
                                            //inchide progress
                                            dialog.dismiss();
                                            Intent intent = new Intent(LoginActivity.this, AdminTabActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            //inchide progress
                                            dialog.dismiss();
                                            changeActivity();
                                        }
                                    }else{
                                        Log.d(TAG, "No document in db on login");
                                    }
                                }
                            }
                        });
                        Toast.makeText(LoginActivity.this, "Te-ai logat!", Toast.LENGTH_SHORT).show();

                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Eroare + "+ message, Toast.LENGTH_SHORT).show();
                        //inchide progress
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    private void changeActivity(){
        //trece la mainactivity
        Intent intent = new Intent(LoginActivity.this, AnActivity.class);
        startActivity(intent);
        finish();
    }
    private void checkUserLoggedIn(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, AnActivity.class));
        }
    }
}
