package com.example.coach.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;

public class MainActivity extends AppCompatActivity {

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
}