package com.example.coach.contract; // On garde 'contract' si le fichier est dans ce dossier

public interface ICalculView {
    /**
     * Affiche le résultat (IMG + Message)
     * Utilise float pour correspondre à Profil.getImg()
     */
    void recapRender(float img, String message);

    /**
     * Remplit le formulaire avec les anciennes données
     */
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);
}