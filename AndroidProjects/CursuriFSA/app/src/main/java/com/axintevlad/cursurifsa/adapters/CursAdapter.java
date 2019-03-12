package com.axintevlad.cursurifsa.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Curs;
import com.axintevlad.cursurifsa.models.Materie;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by vlad__000 on 12.03.2019.
 */
public class CursAdapter extends FirestoreRecyclerAdapter<Curs, CursAdapter.CursHolder> {
    private OnItemClickListener listener;

    public CursAdapter(FirestoreRecyclerOptions<Curs> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CursHolder holder, int position, @NonNull Curs model) {
        holder.textViewTitlu.setText(model.getDenumire());
        holder.textViewLink.setText(model.getLink());
    }

    @NonNull
    @Override
    public CursHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_curs,
                parent, false);
        return new CursHolder(v);
    }


    class CursHolder extends RecyclerView.ViewHolder {
        TextView textViewTitlu;
        TextView textViewLink;


        public CursHolder(View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.curs_titlu);
            textViewLink = itemView.findViewById(R.id.curs_link);
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }
}