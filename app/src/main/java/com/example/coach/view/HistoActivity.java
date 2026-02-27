package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coach.R;
import com.example.coach.model.Profil;
import com.example.coach.presenter.CalculPresenter;
import java.util.ArrayList;

public class HistoActivity extends AppCompatActivity {

    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histo);

        // 1. Initialisation du presenter avec le contexte pour la base de données
        this.presenter = new CalculPresenter(null, getApplicationContext());

        // 2. Récupération des données et remplissage de la liste
        remplirListe();

        // 3. Gestion du bouton Retour
        initRetour();
    }

    /**
     * Récupère les profils et les envoie à l'adapter du RecyclerView
     */
    private void remplirListe() {
        // On demande la liste des profils au presenter
        ArrayList<Profil> lesProfils = presenter.getProfils();

        if (lesProfils != null) {
            // On lie le RecyclerView du XML
            RecyclerView rv = findViewById(R.id.lstHisto);

            // On crée l'adapter avec les données
            HistoListAdapter adapter = new HistoListAdapter(this, lesProfils);

            // On applique l'adapter et le gestionnaire de layout
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    /**
     * Initialise le bouton pour revenir au calcul
     */
    private void initRetour() {
        findViewById(R.id.btnRetourHisto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoActivity.this, CalculActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}