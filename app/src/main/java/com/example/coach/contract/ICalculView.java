package com.example.coach.contract;

public interface ICalculView {
    // Méthode pour afficher le résultat du calcul
    void afficherResultat(Integer poids, Integer taille, Integer age, Integer sexe, Double img, String message);

    // Étape 1C : Méthode pour remplir les champs avec les données sauvegardées
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);
    void recupProfil();
}