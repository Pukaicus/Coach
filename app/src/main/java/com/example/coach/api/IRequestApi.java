package com.example.coach.api;

import com.example.coach.model.Profil;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRequestApi {

    @GET("index.php")
    Call<ResponseApi<Integer>> creerProfil(@Query("champs") String profilJson);

    @GET("index.php?tous")
    Call<ResponseApi<List<Profil>>> getProfils();

    /**
     * Correction : On pointe vers index.php et on passe le JSON dans le paramètre 'champs'
     */
    @DELETE("index.php")
    Call<ResponseApi<Integer>> supprProfil(@Query("champs") String profilJson);
}