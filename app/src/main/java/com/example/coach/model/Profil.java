package com.example.coach.model;

import java.util.Date; // Ajout nécessaire pour la date

public class Profil {

    // Constantes pour les seuils
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;

    // Tableaux pour les messages et images
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // Propriétés
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;
    private double img;
    private int indice;
    private Date dateMesure; // Ajout de la propriété dateMesure

    /**
     * Constructeur modifié : ajoute dateMesure en paramètre
     */
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure; // Valorisation de la date
        this.img = calculImg();
        this.indice = calculIndice();
    }

    /**
     * Calcule l'img (Formule mathématique)
     * @return l'img calculé
     */
    private double calculImg() {
        double tailleM = ((double)taille) / 100.0;
        img = (1.2 * poids / (tailleM * tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
        return img;
    }

    /**
     * Détermine l'indice selon le sexe et les constantes
     * @return 0 (faible), 1 (normal) ou 2 (élevé)
     */
    private int calculIndice() {
        int min, max;
        if (sexe == 0) { // Femme
            min = MIN_FEMME;
            max = MAX_FEMME;
        } else { // Homme
            min = MIN_HOMME;
            max = MAX_HOMME;
        }
        if (img < min) {
            indice = 0;
        } else if (img > max) {
            indice = 2;
        } else {
            indice = 1;
        }
        return indice;
    }

    // --- GETTERS (Accesseurs demandés aux étapes 1B et 1D) ---

    public Integer getPoids() { return poids; } //

    public Integer getTaille() { return taille; } //

    public Integer getAge() { return age; } //

    public Integer getSexe() { return sexe; } //

    public Date getDateMesure() { return dateMesure; } //

    public double getImg() {
        return img;
    }

    public String getMessage() {
        return MESSAGE[indice];
    }

    public String getImage() {
        return IMAGE[indice];
    }

    public boolean normal() {
        return indice == 1;
    }
}