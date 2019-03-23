package com.axintevlad.cursurifsa;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import com.axintevlad.cursurifsa.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by vlad__000 on 22.03.2019.
 */
public class CursuriFsaApplication {
    public  int CURS_FRAGMENT_CODE =11;
    public final static int TEME_FRAGMENT_CODE =22;
    public final static int RESURSE_FRAGMENT_CODE =33;

    private static CursuriFsaApplication instance = null;

    private CursuriFsaApplication (){

    }
    public static CursuriFsaApplication getInstance(){
        if(instance == null){
            instance = new CursuriFsaApplication();
        }
        return instance;
    }

    public void checkElev(final FloatingActionButton fab){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("useri").document(userUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = User.getInstance();
                        user = document.toObject(User.class);

                        if(user.getTip().equals("profesor")){
                            fab.show();
                        }
                    }
                }}});
    }

}
