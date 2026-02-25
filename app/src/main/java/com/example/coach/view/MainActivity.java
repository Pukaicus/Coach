package com.example.coach.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.coach.R;
import com.example.coach.presenter.CalculPresenter;
import com.example.coach.contract.ICalculView;

public class MainActivity extends AppCompatActivity implements ICalculView {

    private CalculPresenter presenter;
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblIMG;
    private ImageView imgSmiley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);
        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdfemme);
        lblIMG = findViewById(R.id.lblIMG);
        imgSmiley = findViewById(R.id.imgSmiley);
        Button btnCalc = findViewById(R.id.btnCalc);

        this.presenter = new CalculPresenter(this);

        // CORRECTION : Appel du nouveau nom de méthode défini en 3C3
        this.presenter.chargerDernierProfil();

        btnCalc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recupProfil();
            }
        });
    }

    @Override
    public void recupProfil() {
        Integer poids = 0, taille = 0, age = 0, sexe = 0;
        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception e) {}

        if (rdHomme.isChecked()) {
            sexe = 1;
        }

        presenter.creerProfil(poids, taille, age, sexe);
    }

    @Override
    public void afficherResultat(Integer poids, Integer taille, Integer age, Integer sexe, Double img, String message) {
        lblIMG.setText(String.format("%.1f", img) + " : " + message);

        if (message.equals("trop faible")) {
            imgSmiley.setImageResource(R.drawable.maigre);
        } else if (message.equals("trop élevé")) {
            imgSmiley.setImageResource(R.drawable.graisse);
        } else {
            imgSmiley.setImageResource(R.drawable.normal);
        }
    }

    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        if (txtPoids != null && txtTaille != null && txtAge != null) {
            txtPoids.setText(poids.toString());
            txtTaille.setText(taille.toString());
            txtAge.setText(age.toString());

            if (sexe == 1) {
                rdHomme.setChecked(true);
            } else {
                rdFemme.setChecked(true);
            }
        }
    }
}