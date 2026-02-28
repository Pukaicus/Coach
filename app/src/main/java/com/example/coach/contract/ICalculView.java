package com.example.coach.contract;

/**
 * Interface pour la vue de calcul
 */
public interface ICalculView extends IAllView {
    /**
     * Affiche le résultat (IMG + Message)
     */
    void recapRender(float img, String message);

    /**
     * Remplit le formulaire avec les anciennes données
     */
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);
}