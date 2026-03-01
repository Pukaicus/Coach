package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.contract.IAllView;
import com.example.coach.presenter.MainPresenter;

/**
 * Activité du menu principal implémentant IAllView pour les messages
 */
public class MainActivity extends AppCompatActivity implements IAllView {

    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;
    private ImageButton btnPurge;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * Initialisation globale
     */
    private void init() {
        this.presenter = new MainPresenter(this);

        chargeObjetsGraphiques();
        creerMenu();
    }

    /**
     * Récupère les objets graphiques
     */
    private void chargeObjetsGraphiques() {
        btnMonIMG = (ImageButton) findViewById(R.id.btnMonIMG);
        btnMonHistorique = (ImageButton) findViewById(R.id.btnMonHistorique);
        btnPurge = (ImageButton) findViewById(R.id.btnPurge);
    }

    /**
     * Méthode générique pour la redirection
     */
    private void ecouteMenu(Class classe) {
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent);
    }

    /**
     * Crée les ecoute sur les boutons
     */
    private void creerMenu() {
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));

        btnPurge.setOnClickListener(v -> presenter.purge());
    }

    /**
     * Affiche les messages demander
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}