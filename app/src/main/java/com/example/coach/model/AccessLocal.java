package com.example.coach.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessLocal {

    private String nomBase = "bdCoach.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private static AccessLocal instance;
    private SQLiteDatabase bd;

    private AccessLocal(Context contexte){
        accesBD = new MySQLiteOpenHelper(contexte, nomBase, null, versionBase);
    }

    public static AccessLocal getInstance(Context contexte){
        if(instance == null){
            instance = new AccessLocal(contexte);
        }
        return instance;
    }

    /**
     * Ajout d'un profil dans la base locale (SQLite) ET envoi vers MySQL (PC)
     */
    public void ajout(Profil profil){
        // 1. Sauvegarde locale SQLite pour que l'appli garde les données sans internet
        bd = accesBD.getWritableDatabase();
        String req = "replace into profil (datemesure, poids, taille, age, sexe) values ";
        req += "(\""+profil.getDateMesure().toString()+"\","+profil.getPoids()+","+profil.getTaille()+","+profil.getAge()+","+profil.getSexe()+")";
        bd.execSQL(req);

        // 2. ENVOI VERS MYSQL via Retrofit
        IRequestApi service = CoachApi.getRetrofit().create(IRequestApi.class);
        String profilJson = CoachApi.getGson().toJson(profil);

        // On utilise "champs" car ton PHP l'attend
        Call<ResponseApi<Integer>> call = service.creerProfil(profilJson);

        call.enqueue(new Callback<ResponseApi<Integer>>() {
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {
                if (response.isSuccessful()) {
                    Log.d("API_DEBUG", "Réussite : Le profil est bien arrivé sur le Localhost du PC !");
                } else {
                    Log.e("API_DEBUG", "Erreur serveur : " + response.code() + ". Vérifie ton PHP !");
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable t) {
                Log.e("API_DEBUG", "Échec connexion : " + t.getMessage());
            }
        });
    }

    /**
     * --- NOUVEAU : Récupère TOUS les profils pour remplir la liste HistoActivity ---
     * C'est cette méthode qui répare l'erreur dans Controle.java
     */
    public ArrayList<Profil> getLesProfils() {
        bd = accesBD.getReadableDatabase();
        ArrayList<Profil> lesProfils = new ArrayList<>();
        String req = "select * from profil order by datemesure desc";
        Cursor curseur = bd.rawQuery(req, null);

        while (curseur.moveToNext()) {
            // On récupère les colonnes de la base locale
            Date date = new Date(); // Pour l'instant on met la date actuelle
            Integer poids = curseur.getInt(1);
            Integer taille = curseur.getInt(2);
            Integer age = curseur.getInt(3);
            Integer sexe = curseur.getInt(4);
            Profil profil = new Profil(date, poids, taille, age, sexe);
            lesProfils.add(profil);
        }
        curseur.close();
        return lesProfils;
    }

    public Profil recupDernier(){
        bd = accesBD.getReadableDatabase();
        Profil profil = null;
        String req = "select * from profil";
        Cursor curseur = bd.rawQuery(req, null);

        if (curseur.getCount() > 0) {
            curseur.moveToLast();
            Date date = new Date();
            Integer poids = curseur.getInt(1);
            Integer taille = curseur.getInt(2);
            Integer age = curseur.getInt(3);
            Integer sexe = curseur.getInt(4);
            profil = new Profil(date, poids, taille, age, sexe);
        }
        curseur.close();
        return profil;
    }
}