package com.example.coach.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe représentant un profil utilisateur et ses calculs d'IMG
 * Implémente Serializable pour permettre le transfert entre activités
 */
public class Profil implements Serializable {

    // Constantes pour les calculs et messages
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // Propriétés
    private Integer id;
    private Date dateMesure;
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;

    /**
     * Constructeur pour créer un nouveau profil
     */
    public Profil(Date dateMesure, Integer poids, Integer taille, Integer age, Integer sexe) {
        this.dateMesure = dateMesure;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
    }

    /**
     * Calcul de l'IMG selon la formule officielle
     */
    private double calculImg() {
        double tailleM = ((double) taille) / 100.0;
        // Formule : (1.2 * IMC) + (0.23 * age) - (10.83 * sexe) - 5.4
        return (1.2 * poids / (tailleM * tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
    }

    /**
     * Détermine
     */
    private int calculIndice() {
        double imgCalculée = calculImg();
        // Sexe 0 = Femme, Sexe 1 = Homme
        int min = (sexe == 0) ? MIN_FEMME : MIN_HOMME;
        int max = (sexe == 0) ? MAX_FEMME : MAX_HOMME;

        if (imgCalculée < min) return 0;
        if (imgCalculée > max) return 2;
        return 1;
    }


    public Integer getId() { return id; }
    public Date getDateMesure() { return dateMesure; }
    public Integer getPoids() { return poids; }
    public Integer getTaille() { return taille; }
    public Integer getAge() { return age; }
    public Integer getSexe() { return sexe; }

    /**
     * L'img est recalculé à chaque fois que getImg est appelée
     */
    public double getImg() {
        return calculImg();
    }

    public String getMessage() {
        return MESSAGE[calculIndice()];
    }

    public String getImage() {
        return IMAGE[calculIndice()];
    }
}