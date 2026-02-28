package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coach.R;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;
import java.util.List;

/**
 * Activité gérant l'affichage de l'historique via un RecyclerView
 */
public class HistoActivity extends AppCompatActivity implements IHistoView {

    private HistoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);
        init();
    }

    /**
     * Initialisation du presenter
     */
    private void init() {
        presenter = new HistoPresenter(this);
        presenter.chargerProfils();
    }

    /**
     * Affiche la liste des profils reçue du Presenter
     */
    @Override
    public void afficherListe(List<Profil> profils) {
        if (profils != null) {
            RecyclerView lstHisto = (RecyclerView) findViewById(R.id.lstHisto);

            HistoListAdapter adapter = new HistoListAdapter(profils, this, presenter);
            lstHisto.setAdapter(adapter);
            lstHisto.setLayoutManager(new LinearLayoutManager(HistoActivity.this));
        }
    }

    /**
     * Affiche un message d'information ou d'erreur
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Redirige vers CalculActivity pour afficher le profil sélectionné
     */
    @Override
    public void transfertProfil(Profil profil) {
        Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
        intent.putExtra("profil", profil);
        startActivity(intent);
    }
}