package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;

public class MainActivity extends AppCompatActivity {

    // Déclaration des propriétés privées
    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(); // Appelé à la fin de onCreate
    }

    /**
     * Initialisation globale
     */
    private void init() {
        chargeObjetsGraphiques(); // Récupère les objets
        creerMenu(); // Crée les écoutes
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
     * @param classe l'activité vers laquelle se diriger
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
        // Ligne mise en commentaire car HistoActivity n'est pas encore créée
        // btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
    }
}