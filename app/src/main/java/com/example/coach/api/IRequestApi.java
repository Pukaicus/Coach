package com.example.coach.api;

import com.example.coach.model.Profil;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IRequestApi {

    // Envoi des données de calcul vers MySQL
    @GET("index.php")
    Call<ResponseApi<Integer>> creerProfil(@Query("champs") String profilJson);

    // Récupération de l'historique complet
    // On ajoute souvent un paramètre pour dire au PHP de renvoyer la liste
    @GET("index.php?tous")
    Call<ResponseApi<List<Profil>>> getProfils();
}