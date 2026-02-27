package com.example.coach.controller;

import android.content.Context;
import android.util.Log;
import com.example.coach.api.CoachApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.AccessLocal; // Ajouté pour la base locale
import com.example.coach.model.Profil;
import java.util.ArrayList; // Changé de List à ArrayList pour correspondre au Presenter
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public final class Controle {
    private static Controle instance = null;
    private static IRequestApi requestApi;
    private ICalculView vue;
    private static AccessLocal accesLocal; // Instance de la base SQLite

    private Controle() {
        super();
    }

    /**
     * Récupération de l'instance unique (Singleton)
     */
    public static Controle getInstance(Context contexte) {
        if (Controle.instance == null) {
            Controle.instance = new Controle();
            requestApi = CoachApi.getRetrofit().create(IRequestApi.class);
            // On initialise l'accès à la base locale SQLite
            accesLocal = AccessLocal.getInstance(contexte);
        }
        return Controle.instance;
    }

    // Garde cette méthode pour la compatibilité avec ton code existant
    public static Controle getInstance() {
        return instance;
    }

    public void setVue(ICalculView vue) {
        this.vue = vue;
    }

    /**
     * Envoi d'un profil vers l'API MySQL
     */
    public void creerProfil(Profil profil) {
        // Sauvegarde locale d'abord
        accesLocal.ajout(profil);

        // Puis envoi vers l'API
        String profilJson = CoachApi.getGson().toJson(profil);
        Call<ResponseApi<Integer>> call = requestApi.creerProfil(profilJson);

        call.enqueue(new Callback<ResponseApi<Integer>>() {
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API", "Succès envoi MySQL : " + response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable t) {
                Log.e("API", "Échec d'accès à l'api (Vérifie ton IP !)", t);
            }
        });
    }

    /**
     * --- LA MÉTHODE QUI MANQUAIT : Retourne l'historique ---
     * C'est elle qui répare l'erreur de ton CalculPresenter
     */
    public ArrayList<Profil> getLesProfils() {
        // On demande la liste à la base locale (SQLite) pour l'afficher sur l'Oppo
        return accesLocal.getLesProfils();
    }

    public void chargerDernierProfil() {
        // Tentative de récupération locale d'abord
        Profil dernier = accesLocal.recupDernier();

        if(dernier != null && vue != null) {
            vue.remplirChamps(dernier.getPoids(), dernier.getTaille(), dernier.getAge(), dernier.getSexe());
        }

        // Optionnel : Mise à jour depuis l'API MySQL
        Call<ResponseApi<List<Profil>>> call = requestApi.getProfils();
        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call, Response<ResponseApi<List<Profil>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Profil> profils = response.body().getResult();
                    if (profils != null && !profils.isEmpty()) {
                        Log.d("API", "Historique récupéré depuis MySQL");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable t) {
                Log.e("API", "Erreur réseau historique", t);
            }
        });
    }
}