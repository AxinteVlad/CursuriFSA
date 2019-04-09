package com.axintevlad.cursurifsa.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.activities.MaterieActivity;
import com.axintevlad.cursurifsa.models.Materie;
import com.axintevlad.cursurifsa.models.Subscription;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestoreDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad__000 on 04-Mar-19.
 */
public class MaterieAdapter  extends FirestoreRecyclerAdapter<Materie, MaterieAdapter.MaterieHolder> {
    private static final String TAG = "MaterieAdapter";
    private OnItemClickListener listener;
    private OnSubscribeClickListener subscribeListener;
    boolean state = false;
    public MaterieAdapter(FirestoreRecyclerOptions<Materie> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MaterieHolder holder, int position, @NonNull final Materie model) {
        holder.textViewTitlu.setText(model.getTitlu());
        holder.textViewDescriere.setText(model.getDescriere());
        Picasso.get().load(model.getImageUrl()).fit().into(holder.imageViewHeader);
        Log.d(TAG, "MaterieUid: "+model.getMaterieUid());
        holder.btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!state){
                    holder.btnSubscribe.setBackgroundResource(R.drawable.ic_subscribe_on);
                    state = true;
                    FirebaseMessaging.getInstance().subscribeToTopic(model.getMaterieUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("subscribeId",model.getMaterieUid());
                            data.put("status",true);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("useri").document(FirebaseAuth.getInstance().getUid())
                                    .collection("subs").document(model.getMaterieUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Sanatate");
                                }
                            });
                        }
                    });
                }else{
                    holder.btnSubscribe.setBackgroundResource(R.drawable.ic_subscribe_off);
                    state = false;
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(model.getMaterieUid()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            HashMap<String, Object> data = new HashMap<>();
                            data.put("subscribeId",model.getMaterieUid());
                            data.put("status",false);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("useri").document(FirebaseAuth.getInstance().getUid())
                                    .collection("subs").document(model.getMaterieUid()).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Sanatate");
                                }
                            });
                        }
                    });
                }
            }
        });
        FirebaseFirestore db =  FirebaseFirestore.getInstance();
        CollectionReference subsRef = db.collection("useri").document(FirebaseAuth.getInstance().getUid()).collection("subs");
        subsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot document : task.getResult()){
                        Subscription sub = document.toObject(Subscription.class);
                        Log.d(TAG, "SubId "+sub.getSubscribeId());
                        Log.d(TAG, "MaterieUid: "+model.getMaterieUid());
                        if(sub.isStatus()&& sub.getSubscribeId().equals(model.getMaterieUid())){
                            holder.btnSubscribe.setBackgroundResource(R.drawable.ic_subscribe_on);
                        }
                    }
                }
            }
        });



    }

    @NonNull
    @Override
    public MaterieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_materie,
                parent, false);
        return new MaterieHolder(v);
    }


    class MaterieHolder extends RecyclerView.ViewHolder {
        TextView textViewTitlu;
        TextView textViewDescriere;
        ImageView imageViewHeader;
        Button btnSubscribe;

        public MaterieHolder(View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.text_titlu);
            textViewDescriere = itemView.findViewById(R.id.text_descriere);
            imageViewHeader = itemView.findViewById(R.id.imageView_materie_header);

            btnSubscribe = itemView.findViewById(R.id.btn_subscribe);

            btnSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    subscribeListener.onSubscribeClick(getSnapshots().getSnapshot(position),position);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(getSnapshots().getSnapshot(position),position);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public interface OnSubscribeClickListener{
        void onSubscribeClick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }
    public void setOnSubscribeClickListener(OnSubscribeClickListener listener){
        this.subscribeListener =listener;
    }
}