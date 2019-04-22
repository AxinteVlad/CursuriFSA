package com.axintevlad.cursurifsa.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(context, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.admin_item_menu, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Schimbare Drepturi")){
                            if(model.getTip().equals("elev")){
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which){
                                            case DialogInterface.BUTTON_POSITIVE:
                                                Map<String,Object> data =new HashMap<>();
                                                data.put("tip","profesor");
                                                db.collection("useri").document(model.getUserUID()).set(data, SetOptions.merge());
                                                Toast.makeText(context,"Userul "+model.getEmail()+" este acum profesor!",Toast.LENGTH_SHORT).show();
                                                popup.dismiss();
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                popup.dismiss();
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Utilizatorul va deveni PROFESOR?").setPositiveButton("Da", dialogClickListener)
                                        .setNegativeButton("Nu", dialogClickListener).show();
                                return true;
                            }else {

                                popup.dismiss();
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                Map<String, Object> data = new HashMap<>();
                                                data.put("tip", "elev");
                                                db.collection("useri").document(model.getUserUID()).set(data, SetOptions.merge());
                                                Toast.makeText(context, "Userul " + model.getEmail() + " este acum elev!", Toast.LENGTH_SHORT).show();
                                                popup.dismiss();
                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                popup.dismiss();
                                                break;
                                        }

                                    }
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Utilizatorul va deveni ELEV?").setPositiveButton("Da", dialogClickListener)
                                        .setNegativeButton("Nu", dialogClickListener).show();
                                return true;

                            }
                        }

                        return false;
                    }
                });
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
        ImageButton imageButton;


        public UserHolder(View itemView) {
            super(itemView);
            textViewEmail = itemView.findViewById(R.id.textview_admin_email);
            imageButton = itemView.findViewById(R.id.imagebutton_threedots);

        }
    }

}
