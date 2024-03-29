package com.axintevlad.cursurifsa.adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Materie;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Created by vlad__000 on 04-Mar-19.
 */
public class MaterieAdapter  extends FirestoreRecyclerAdapter<Materie, MaterieAdapter.MaterieHolder> {
    private OnItemClickListener listener;

    public MaterieAdapter(FirestoreRecyclerOptions<Materie> options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MaterieHolder holder, int position, @NonNull Materie model) {
        holder.textViewTitlu.setText(model.getTitlu());
        holder.textViewDescriere.setText(model.getDescriere());
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


        public MaterieHolder(View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.text_titlu);
            textViewDescriere = itemView.findViewById(R.id.text_descriere);
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener =listener;
    }
}