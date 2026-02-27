package com.example.coach.model;

import java.util.Date;

public class Profil {

    // Constantes
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // Propriétés transférées vers l'API
    private Date dateMesure;
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;

    // --- CORRECTION : Propriétés ignorées par Gson donc non envoyées vers l'API ---
    private transient double img;
    private transient int indice;

    public Profil(Date dateMesure, Integer poids, Integer taille, Integer age, Integer sexe) {
        this.dateMesure = dateMesure;
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.img = calculImg();
        this.indice = calculIndice();
    }

    /**
     * Calcul de l'IMG selon la formule officielle
     */
    private double calculImg() {
        double tailleM = ((double)taille) / 100.0;
        return (1.2 * poids / (tailleM * tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
    }

    /**
     * Détermine l'indice (0, 1 ou 2) par rapport aux seuils homme/femme
     */
    private int calculIndice() {
        int min = (sexe == 0) ? MIN_FEMME : MIN_HOMME;
        int max = (sexe == 0) ? MAX_FEMME : MAX_HOMME;
        if (img < min) return 0;
        if (img > max) return 2;
        return 1;
    }

    // --- GETTERS ---
    public Date getDateMesure() { return dateMesure; }
    public Integer getPoids() { return poids; }
    public Integer getTaille() { return taille; }
    public Integer getAge() { return age; }
    public Integer getSexe() { return sexe; }
    public double getImg() { return img; }
    public String getMessage() { return MESSAGE[indice]; }

    /**
     * Retourne le nom de l'image (maigre, normal, graisse) pour la vue
     */
    public String getImage() {
        return IMAGE[indice];
    }

    /**
     * Retourne vrai si l'indice est "normal" (1) pour la vue
     */
    public boolean normal() {
        return indice == 1;
    }
}