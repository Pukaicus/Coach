package com.example.coach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coach.R;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.ViewHolder> {

    private List<Profil> profils;
    private IHistoView vue;
    private HistoPresenter presenter;

    public HistoListAdapter(List<Profil> profils, IHistoView vue, HistoPresenter presenter) {
        this.profils = profils;
        this.vue = vue;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_liste_histo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profil profil = profils.get(position);

        if (profil.getDateMesure() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            holder.txtListDate.setText(sdf.format(profil.getDateMesure()));
        } else {
            holder.txtListDate.setText("Date inconnue");
        }

        holder.txtListIMG.setText(String.format(Locale.getDefault(), "%.1f", profil.getImg()));

        // GESTION SUPPRESSION
        holder.btnListSuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On récupère la position actuelle pour éviter les décalages
                int currentPos = holder.getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION) {
                    presenter.supprProfil(profil, new ICallbackApi<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            profils.remove(currentPos);
                            notifyItemRemoved(currentPos);
                            notifyItemRangeChanged(currentPos, getItemCount());
                        }

                        @Override
                        public void onError() {
                            vue.afficherMessage("Erreur lors de la suppression");
                        }
                    });
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.transfertProfil(profil);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtListDate;
        public final TextView txtListIMG;
        public final ImageButton btnListSuppr;

        public ViewHolder(View itemView) {
            super(itemView);
            txtListDate = (TextView) itemView.findViewById(R.id.txtListDate);
            txtListIMG = (TextView) itemView.findViewById(R.id.txtListIMG);
            btnListSuppr = (ImageButton) itemView.findViewById(R.id.btnListSuppr);
        }
    }
}