package com.axintevlad.cursurifsa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Resurse;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by vlad__000 on 22.03.2019.
 */
public class ResurseAdapter extends FirestoreRecyclerAdapter<Resurse,ResurseAdapter.ResurseHolder> {
    private ResurseAdapter.OnItemClickListener listener;

    public ResurseAdapter(FirestoreRecyclerOptions<Resurse> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ResurseAdapter.ResurseHolder holder, int position, @NonNull Resurse model) {
        holder.textViewTitlu.setText(model.getDenumire());
        holder.textViewLink.setText(model.getLink());
    }

    @NonNull
    @Override
    public ResurseAdapter.ResurseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_resurse,
                parent, false);
        return new ResurseAdapter.ResurseHolder(v);
    }


    class ResurseHolder extends RecyclerView.ViewHolder {
        TextView textViewTitlu;
        TextView textViewLink;


        public ResurseHolder(View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.resurse_titlu);
            textViewLink = itemView.findViewById(R.id.resurse_link);
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

    public void setOnItemClickListener(ResurseAdapter.OnItemClickListener listener){
        this.listener =listener;
    }
}
