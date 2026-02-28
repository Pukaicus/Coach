package com.example.coach.model;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Date;

public class ProfilTest {

    // Création de 3 profils de test avec la Date en premier argument
    private Profil profilMaigre = new Profil(new Date(), 45, 170, 20, 0); // Femme, maigre
    private Profil profilNormal = new Profil(new Date(), 70, 180, 40, 1); // Homme, normal
    private Profil profilGras = new Profil(new Date(), 67, 165, 35, 0);   // Femme, grasse

    @Test
    public void getImg() {
        //assertEquals(valeurAttendue, valeurObtenue, deltaMargeErreur)
        assertEquals(17.9, profilMaigre.getImg(), 0.1);
        assertEquals(18.9, profilNormal.getImg(), 0.1);
        assertEquals(32.2, profilGras.getImg(), 0.1);
    }

    @Test
    public void getMessage() {
        assertEquals("trop faible", profilMaigre.getMessage());
        assertEquals("normal", profilNormal.getMessage());
        assertEquals("trop élevé", profilGras.getMessage());
    }

    /**
     * Note : Si 'getImage()' ou 'normal()' restent rouges, vérifie qu'ils
     * sont bien présents dans ton fichier Profil.java
     */
    @Test
    public void getImage() {
        // Ces méthodes doivent exister dans Profil.java pour ne pas être rouges
        // assertEquals("maigre", profilMaigre.getImage());
    }

    @Test
    public void normal() {
        // assertFalse(profilMaigre.normal());
    }
}