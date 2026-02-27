package com.example.coach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coach.model.Profil;
import java.util.ArrayList;

public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.MyViewHolder> {

    private ArrayList<Profil> lesProfils;
    private Context contexte;

    public HistoListAdapter(Context contexte, ArrayList<Profil> lesProfils) {
        this.lesProfils = lesProfils;
        this.contexte = contexte;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // On utilise un design de ligne simple déjà prêt dans Android
        View v = LayoutInflater.from(contexte).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Profil p = lesProfils.get(position);
        // On affiche la date et l'IMG calculé dans la ligne
        holder.txtLigne.setText(p.getDateMesure().toString() + " : IMG = " + String.format("%.1f", p.getImg()));
    }

    @Override
    public int getItemCount() {
        return lesProfils.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtLigne;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLigne = itemView.findViewById(android.R.id.text1);
        }
    }
}