package com.axintevlad.cursurifsa.activities;

import android.app.AlertDialog;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private Map<String,Object> user = new HashMap<>();
    private FirebaseAuth mAuth;
    private Button signup;
    private TextView emailEditText;
    private TextView passwordEditText;
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
        //progress bar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        final AlertDialog dialog = builder.create();



        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Nu ai introdus un email",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Nu ai introdus o parola",Toast.LENGTH_SHORT).show();
        }
        else{
            dialog.show();
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
                        //retrive token an put in db
                        retriveToken();
                        user.put("userUID",userUID);
                        db.collection("useri/").document(userUID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "User adaugat in bd ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document in bd", e);
                            }
                        });

                        dialog.dismiss();
                        logIn();

                    }else
                        {
                        String message = task.getException().getMessage();
                        Toast.makeText(SignUpActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        }
                }
            });
        }
    }

    private void logIn(){
        //trece la mainactivity
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void retriveToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        final String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        user.put("tokenId",token);
                        // Log
                        Log.d(TAG,"Token: " + token);
                        //do2qj9js4UY:APA91bF9czm_xrIkA745skxquHM6sSigRvWM26IGLe3cI9MP_0LwiQplV_k0fq5hae7-2ZBgmtCyg3AeNYtrc7Cc_wxku1i61LPEdZMCA8mtJCXkzYY-Fd-hnYr7hDSsDsg1eAH9qlA9
                        db.collection("useri").document(userUID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Token adaugat "+ userUID+" in bd ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document in bd", e);
                            }
                        });
                    }
                });
    }


}
