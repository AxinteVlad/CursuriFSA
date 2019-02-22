package com.axintevlad.cursurifsa.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.MainActivity;
import com.axintevlad.cursurifsa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button signup;
    private TextView emailEditText;
    private TextView passwordEditText;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        signup = findViewById(R.id.button_signup);
        emailEditText = findViewById(R.id.text_email);
        passwordEditText = findViewById(R.id.text_password);
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

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Inregistrat", Toast.LENGTH_SHORT).show();
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
