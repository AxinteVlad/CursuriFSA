package com.axintevlad.cursurifsa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Subscription;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad__000 on 12.04.2019.
 */
public class SubscriptiiAdapter extends FirestoreRecyclerAdapter<Subscription, SubscriptiiAdapter.SubscriptionHolder> {
    private SubscriptiiAdapter.OnItemClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth user = FirebaseAuth.getInstance();
    public SubscriptiiAdapter(FirestoreRecyclerOptions<Subscription> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubscriptiiAdapter.SubscriptionHolder holder, int position, @NonNull final Subscription model) {

        holder.textViewNume.setText(model.getNume());
        holder.imageViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> data =new HashMap<>();
                data.put("status",false);
                db.collection("useri").document(user.getUid())
                        .collection("subs").document(model.getSubscribeId()).set(data, SetOptions.merge());
            }
        });

       // holder.textViewLink.setText(model.isStatus());
    }

    @NonNull
    @Override
    public SubscriptiiAdapter.SubscriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscriptii_item,
                parent, false);
        return new SubscriptiiAdapter.SubscriptionHolder(v);
    }


    class SubscriptionHolder extends RecyclerView.ViewHolder {
        TextView textViewNume;
        ImageView imageViewDismiss;


        public SubscriptionHolder(View itemView) {
            super(itemView);
            textViewNume = itemView.findViewById(R.id.textview_subscriptii_nume);
            imageViewDismiss = itemView.findViewById(R.id.imageview_subscriptii_dismiss);
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
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(SubscriptiiAdapter.OnItemClickListener listener){
        this.listener =listener;
    }
}
