package com.example.coach.presenter;

import android.content.Context;
import com.example.coach.contract.ICalculView;
import com.example.coach.data.ProfilDAO; // On importe le nouveau package
import com.example.coach.model.Profil;
import java.util.Date;

public class CalculPresenter {

    private Profil profil;
    private ICalculView view;
    // 3C1. Déclaration de la propriété profilDAO
    private ProfilDAO profilDAO;

    /**
     * Constructeur
     * @param view la MainActivity qui implémente ICalculView
     */
    public CalculPresenter(ICalculView view) {
        this.view = view;
        // 3C1. Valorisation de la propriété profilDAO avec le contexte
        this.profilDAO = new ProfilDAO((Context)view);
    }

    /**
     *  Crée un nouveau profil et l'enregistre dans la base
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        // Création de l'objet profil
        this.profil = new Profil(poids, taille, age, sexe, new Date());

        // Enregistrement via le DAO (Méthode insertProfil demandée en 3B7)
        profilDAO.insertProfil(profil);

        // Mise à jour de la vue pour afficher le résultat
        this.view.afficherResultat(poids, taille, age, sexe, profil.getImg(), profil.getMessage());
    }

    /**
     *  Récupère le dernier profil enregistré et l'envoie à la vue
     */
    public void chargerDernierProfil() {
        // Récupération via le DAO (Méthode getLastProfil demandée en 3B8)
        this.profil = profilDAO.getLastProfil();

        if (profil != null) {
            // Si un profil existe, on remplit les champs de la vue
            view.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }
    }

    public Profil getProfil() {
        return profil;
    }
}