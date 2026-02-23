package com.example.coach.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.presenter.CalculPresenter;
import com.example.coach.contract.ICalculView; // Ajout de l'import de l'interface

// On ajoute "implements ICalculView" pour lier la vue au contrat
public class MainActivity extends AppCompatActivity implements ICalculView {

    // Propriété pour le lien avec le contrôleur
    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); // Appelle l'initialisation
    }

    /**
     * Initialisation des liens avec les objets graphiques et les événements
     */
    private void init() {
        // Initialisation du presenter avec le contexte (this)
        this.presenter = new CalculPresenter(this);
        // On demande au presenter de charger d'éventuelles données sauvegardées
        this.presenter.chargerProfil();

        // On récupère le bouton de calcul
        Button btnCalc = findViewById(R.id.btnCalc);
        TextView lblIMG = findViewById(R.id.lblIMG);

        // Événement sur le clic du bouton
        btnCalc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pour le test, on affiche en dur la valeur attendue
                // Plus tard, nous utiliserons le "Presenter" pour faire le vrai calcul
                lblIMG.setText("18.9 : IMG normal");
            }
        });
    }

    /**
     * Affiche le résultat du calcul (Méthode du contrat)
     */
    @Override
    public void afficherResultat(Integer poids, Integer taille, Integer age, Integer sexe, Double img, String message) {
        // Sera complété plus tard pour l'affichage réel
    }

    /**
     * Remplit les champs avec les informations récupérées (Méthode du contrat)
     */
    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        // Récupération des objets graphiques
        EditText txtPoids = findViewById(R.id.txtPoids);
        EditText txtTaille = findViewById(R.id.txtTaille);
        EditText txtAge = findViewById(R.id.txtAge);
        RadioButton rdHomme = findViewById(R.id.rdHomme);
        RadioButton rdFemme = findViewById(R.id.rdfemme); // Minuscules comme dans ton XML

        // Sécurité : on ne remplit que si les objets existent pour éviter le crash
        if (txtPoids != null && txtTaille != null && txtAge != null) {
            txtPoids.setText(poids.toString());
            txtTaille.setText(taille.toString());
            txtAge.setText(age.toString());

            // Test sur le sexe pour cocher le bon RadioButton
            if (sexe == 1 && rdHomme != null) {
                rdHomme.setChecked(true);
            } else if (rdFemme != null) {
                rdFemme.setChecked(true);
            }
        }
    }
}