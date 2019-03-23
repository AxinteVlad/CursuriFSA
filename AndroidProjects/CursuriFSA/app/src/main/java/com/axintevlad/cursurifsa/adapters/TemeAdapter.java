package com.axintevlad.cursurifsa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Teme;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by vlad__000 on 22.03.2019.
 */
public class TemeAdapter extends FirestoreRecyclerAdapter<Teme, TemeAdapter.TemeHolder> {
    private OnItemClickListener listener;

    public TemeAdapter(FirestoreRecyclerOptions<Teme> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TemeAdapter.TemeHolder holder, int position, @NonNull Teme model) {
        holder.textViewTitlu.setText(model.getDenumire());
        holder.textViewLink.setText(model.getLink());
    }

    @NonNull
    @Override
    public TemeAdapter.TemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_teme,
                parent, false);
        return new TemeHolder(v);
    }


    class TemeHolder extends RecyclerView.ViewHolder {
        TextView textViewTitlu;
        TextView textViewLink;


        public TemeHolder(View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.teme_titlu);
            textViewLink = itemView.findViewById(R.id.teme_link);
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

    public void setOnItemClickListener(TemeAdapter.OnItemClickListener listener){
        this.listener =listener;
    }
}
