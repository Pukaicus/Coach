package com.example.coach.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Date;

public class AccessLocal {

    // propriétés
    private String nomBase = "bdCoach.sqlite";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private static AccessLocal instance;
    private SQLiteDatabase bd;

    /**
     * Constructeur
     * @param contexte
     */
    private AccessLocal(Context contexte){
        accesBD = new MySQLiteOpenHelper(contexte, nomBase, null, versionBase);
    }

    /**
     * Création de l'instance unique (Singleton)
     * @param contexte
     * @return instance
     */
    public static AccessLocal getInstance(Context contexte){
        if(instance == null){
            instance = new AccessLocal(contexte);
        }
        return instance;
    }

    /**
     * Ajout d'un profil dans la BD
     * @param profil
     */
    public void ajout(Profil profil){
        bd = accesBD.getWritableDatabase();
        // Utilisation de replace pour écraser si la date (clé primaire) existe déjà
        // Cela évite l'erreur UNIQUE constraint failed vue dans le Logcat
        String req = "replace into profil (datemesure, poids, taille, age, sexe) values ";
        req += "(\""+profil.getDateMesure().toString()+"\","+profil.getPoids()+","+profil.getTaille()+","+profil.getAge()+","+profil.getSexe()+")";
        bd.execSQL(req);
    }

    /**
     * Récupération du dernier profil de la BD
     * @return profil
     */
    public Profil recupDernier(){
        bd = accesBD.getReadableDatabase();
        Profil profil = null;
        String req = "select * from profil";
        Cursor curseur = bd.rawQuery(req, null);

        // On vérifie que la base n'est pas vide (Count > 0) pour éviter le crash au premier lancement
        if (curseur.getCount() > 0) {
            curseur.moveToLast();
            Date date = new Date();
            // On récupère les colonnes dans l'ordre de création (1:poids, 2:taille, etc.)
            Integer poids = curseur.getInt(1);
            Integer taille = curseur.getInt(2);
            Integer age = curseur.getInt(3);
            Integer sexe = curseur.getInt(4);
            profil = new Profil(poids, taille, age, sexe, date);
        }
        curseur.close();
        return profil;
    }
}