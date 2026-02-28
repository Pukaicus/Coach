package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.CalculPresenter;

public class CalculActivity extends AppCompatActivity implements ICalculView {

    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        this.presenter = new CalculPresenter(this, getApplicationContext());

        init();
    }

    /**
     * Initialisation des liens et récupération des données
     */
    private void init() {
        recupProfil();

        ecouteCalcul();

        View imageHisto = findViewById(R.id.btnHisto);
        if (imageHisto != null) {
            imageHisto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalculActivity.this, HistoActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void recupProfil() {
        Profil profil = (Profil) getIntent().getSerializableExtra("profil");

        if (profil != null) {
            remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        } else {
            presenter.chargerDernierProfil();
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
                    Integer poids = Integer.parseInt(((EditText)findViewById(R.id.txtPoids)).getText().toString());
                    Integer taille = Integer.parseInt(((EditText)findViewById(R.id.txtTaille)).getText().toString());
                    Integer age = Integer.parseInt(((EditText)findViewById(R.id.txtAge)).getText().toString());
                    Integer sexe = ((RadioButton)findViewById(R.id.rdHomme)).isChecked() ? 1 : 0;

                    presenter.creerProfil(poids, taille, age, sexe);

                } catch (Exception e) {
                    Log.e("API_DEBUG", "Erreur de saisie : " + e.getMessage());
                }
            }
        });
    }

    /**
     * Affiche le résultat et l'émoji
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
     * Remplit le formulaire
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

    /**
     * Affiche un message temporaire
     */
    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}