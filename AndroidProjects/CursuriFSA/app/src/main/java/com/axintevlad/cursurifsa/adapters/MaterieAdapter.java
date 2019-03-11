package com.axintevlad.cursurifsa.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Materie;
import java.util.List;

/**
 * Created by vlad__000 on 04-Mar-19.
 */
public class MaterieAdapter  extends RecyclerView.Adapter<MaterieAdapter.ViewHolder> {

    private List<Materie> materieList;
    private Context context;

    public MaterieAdapter(List<Materie> materieList, Context context) {
        this.materieList = materieList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_materie,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Materie materie = materieList.get(position);
        viewHolder.textViewTitlu.setText(materie.getNume());
        viewHolder.textViewDescriere.setText(materie.getDescriere());
    }

    @Override
    public int getItemCount() {
        return materieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitlu;
        public TextView textViewDescriere;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitlu = itemView.findViewById(R.id.text_titlu);
            textViewDescriere = itemView.findViewById(R.id.text_descriere);
        }
    }

    private List<Materie> mList;

}