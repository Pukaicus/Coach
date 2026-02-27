package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.presenter.CalculPresenter;

public class CalculActivity extends AppCompatActivity implements ICalculView {

    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        // --- CORRECTION CRUCIALE : On passe le contexte pour initialiser le Singleton Controle ---
        // Cela évite le NullPointerException ligne 39 dans CalculPresenter
        this.presenter = new CalculPresenter(this, getApplicationContext());

        init();
    }

    /**
     * Initialisation des liens et chargement du dernier profil
     */
    private void init() {
        // Charge les données depuis SQLite ou l'API au démarrage
        presenter.chargerDernierProfil();
        ecouteCalcul();

        // Gestion du clic sur l'icône de l'historique (le presse-papier)
        View imageHisto = findViewById(R.id.btnHisto);
        if (imageHisto != null) {
            imageHisto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Navigation vers l'écran HistoActivity
                    Intent intent = new Intent(CalculActivity.this, HistoActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Récupère les saisies et demande le calcul au presenter
     */
    private void ecouteCalcul() {
        findViewById(R.id.btnCalc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lecture des saisies utilisateur
                    Integer poids = Integer.parseInt(((EditText)findViewById(R.id.txtPoids)).getText().toString());
                    Integer taille = Integer.parseInt(((EditText)findViewById(R.id.txtTaille)).getText().toString());
                    Integer age = Integer.parseInt(((EditText)findViewById(R.id.txtAge)).getText().toString());
                    Integer sexe = ((RadioButton)findViewById(R.id.rdHomme)).isChecked() ? 1 : 0;

                    // Lancement du calcul et de l'envoi MySQL via le presenter
                    presenter.creerProfil(poids, taille, age, sexe);

                } catch (Exception e) {
                    // Affiche l'erreur si un champ est vide
                    Log.e("API_DEBUG", "Erreur de saisie : " + e.getMessage());
                }
            }
        });
    }

    /**
     * Affiche le résultat et l'émoji correspondant
     */
    @Override
    public void recapRender(float img, String message) {
        ((TextView)findViewById(R.id.lblIMG)).setText(String.format("%.1f", img) + " : IMG " + message);

        ImageView imgSmiley = findViewById(R.id.imgSmiley);
        if (message.equals("normal")) {
            imgSmiley.setImageResource(R.drawable.normal);
        } else if (message.equals("trop faible")) {
            imgSmiley.setImageResource(R.drawable.maigre);
        } else {
            imgSmiley.setImageResource(R.drawable.graisse);
        }
    }

    /**
     * Remplit le formulaire au démarrage avec les données du dernier profil
     */
    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        ((EditText)findViewById(R.id.txtPoids)).setText(poids.toString());
        ((EditText)findViewById(R.id.txtTaille)).setText(taille.toString());
        ((EditText)findViewById(R.id.txtAge)).setText(age.toString());
        if (sexe == 1) {
            ((RadioButton)findViewById(R.id.rdHomme)).setChecked(true);
        } else {
            ((RadioButton)findViewById(R.id.rdfemme)).setChecked(true);
        }
    }
}