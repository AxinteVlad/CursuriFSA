package com.axintevlad.cursurifsa.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.axintevlad.cursurifsa.R;
import com.axintevlad.cursurifsa.models.Subscription;
import com.axintevlad.cursurifsa.models.User;
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
public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder> {
    private UserAdapter.OnItemClickListener listener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth user = FirebaseAuth.getInstance();
    private Context context;
    public UserAdapter(FirestoreRecyclerOptions<User> options, Context context){
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull final UserAdapter.UserHolder holder, int position, @NonNull final User model) {

        holder.textViewEmail.setText(model.getEmail());
        if(model.getTip().equals("profesor")){
            holder.switchTip.setChecked(true);
        }
        else {
            holder.switchTip.setChecked(false);
        }
        holder.switchTip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.switchTip.isChecked()){
                    Map<String,Object> data =new HashMap<>();
                    data.put("tip","profesor");
                    db.collection("useri").document(model.getUserUID()).set(data, SetOptions.merge());
                    Toast.makeText(context,"Userul "+model.getEmail()+" este profesor!",Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,Object> data =new HashMap<>();
                    data.put("tip","elev");
                    db.collection("useri").document(model.getUserUID()).set(data, SetOptions.merge());
                    Toast.makeText(context,"Userul "+model.getEmail()+" este elev!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_item,
                parent, false);
        return new UserAdapter.UserHolder(v);
    }


    class UserHolder extends RecyclerView.ViewHolder {
        TextView textViewEmail;
        Switch switchTip;


        public UserHolder(View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.textview_admin_email);
            switchTip = itemView.findViewById(R.id.switch_admin_tip);
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

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener){
        this.listener =listener;
    }
}
