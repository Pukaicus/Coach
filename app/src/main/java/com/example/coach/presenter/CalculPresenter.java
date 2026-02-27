package com.example.coach.presenter;

import android.content.Context;
import com.example.coach.contract.ICalculView;
import com.example.coach.controller.Controle;
import com.example.coach.model.Profil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Presenter pour gérer la logique entre la vue et le modèle
 */
public class CalculPresenter {

    private Profil profil;
    private ICalculView view;
    private Controle controle;

    /**
     * Constructeur avec initialisation du contrôleur via le contexte
     * @param view l'interface de la vue
     * @param contexte le contexte de l'application
     */
    public CalculPresenter(ICalculView view, Context contexte) {
        this.view = view;
        this.controle = Controle.getInstance(contexte);
    }

    /**
     * Crée un profil, l'enregistre localement et l'envoie à l'API
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        this.profil = new Profil(new Date(), poids, taille, age, sexe);

        // Enregistrement via le contrôleur (SQLite + API MySQL)
        if(this.controle != null) {
            this.controle.creerProfil(profil);
        }

        // Affichage immédiat du résultat dans la vue
        this.view.recapRender((float)profil.getImg(), profil.getMessage());
    }

    /**
     * Charge les profils depuis l'API et affiche le plus récent
     */
    public void chargerDernierProfil() {
        if(this.controle != null) {
            // Récupération de la liste complète des profils
            List<Profil> profils = controle.getLesProfils();

            // Si la liste n'est pas vide, on cherche le plus récent par date
            if (profils != null && !profils.isEmpty()) {
                Profil dernier = Collections.max(
                        profils,
                        (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                );

                // On remplit les champs de la vue avec ce dernier profil
                this.view.remplirChamps(
                        dernier.getPoids(),
                        dernier.getTaille(),
                        dernier.getAge(),
                        dernier.getSexe()
                );
            }
        }
    }

    /**
     * Récupère la liste des profils pour l'Historique
     */
    public ArrayList<Profil> getProfils() {
        if(this.controle != null) {
            return (ArrayList<Profil>) controle.getLesProfils();
        }
        return new ArrayList<>();
    }

    public Profil getProfil() {
        return profil;
    }
}