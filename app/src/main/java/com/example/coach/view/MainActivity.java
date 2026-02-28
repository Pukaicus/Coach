package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;

public class MainActivity extends AppCompatActivity {


    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;

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
        chargeObjetsGraphiques();
        creerMenu();
    }

    /**
     * Récupère les objets graphiques
     */
    private void chargeObjetsGraphiques() {
        btnMonIMG = (ImageButton) findViewById(R.id.btnMonIMG);
        btnMonHistorique = (ImageButton) findViewById(R.id.btnMonHistorique);
    }

    /**
     * Méthode générique pour la redirection
     */
    private void ecouteMenu(Class classe) {
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent); // Méthode de la classe mère
    }

    /**
     * Crée les écoutes sur les boutons
     */
    private void creerMenu() {
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
    }
}