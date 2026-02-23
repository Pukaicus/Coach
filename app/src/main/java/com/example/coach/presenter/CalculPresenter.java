package com.example.coach.presenter;

import android.content.SharedPreferences;
import com.example.coach.model.Profil;
import com.example.coach.contract.ICalculView; // Importation de l'interface
import com.google.gson.Gson;
import android.content.Context;
import java.util.Date; // Import nécessaire pour la date

public class CalculPresenter {

    private Profil profil;
    private static final String NOM_FIC = "coach_fic";
    private static final String PROFIL_CLE = "profil_json";
    private Gson gson;
    private SharedPreferences prefs;
    private Context context; // Ajout de la propriété context pour le chargement

    /**
     * Constructeur : initialise le contexte et les outils de sauvegarde
     * @param context le contexte de l'application (la MainActivity)
     */
    public CalculPresenter(Context context) {
        this.context = context; // Mémorisation du contexte indispensable pour le cast
        this.prefs = context.getSharedPreferences(NOM_FIC, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * Crée un nouveau profil et le sauvegarde immédiatement
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        // C'est ici qu'on ajoute la date du jour avec 'new Date()'
        this.profil = new Profil(poids, taille, age, sexe, new Date());
        // Appel de la méthode de sauvegarde dès que le profil est créé
        sauvegarderProfil(profil);
    }

    /**
     * Méthode privée pour sauvegarder le profil dans le fichier SharedPreferences
     * @param profil l'objet profil à sérialiser
     */
    private void sauvegarderProfil(Profil profil) {
        // On transforme l'objet profil en format JSON (texte)
        String json = gson.toJson(profil);
        // On l'ajoute dans le dictionnaire du fichier et on enregistre avec apply()
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }

    /**
     * Récupération du profil sauvegardé dans le téléphone
     */
    public void chargerProfil() {
        // On récupère la chaîne json dans les préférences
        String json = prefs.getString(PROFIL_CLE, null);

        // Si la chaîne n'est pas nulle, on la transforme en objet Profil
        if (json != null) {
            Profil profil = gson.fromJson(json, Profil.class);
            // On demande à la vue de remplir les champs avec les données récupérées
            // On "cast" le context en ICalculView pour accéder à remplirChamps
            ((ICalculView)context).remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }
    }
}