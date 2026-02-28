package com.example.coach.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe d'accès à l'API via Retrofit
 */
public class CoachApi {

    /**
     * URL de l'API
     */
    private static final String API_URL = "http://192.168.1.16/rest_coach/src/";

    /**
     * Instance unique de l'objet Retrofit
     */
    private static Retrofit retrofit = null;

    /**
     * Objet de conversion JSON paramétré pour les dates MySQL
     */
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * Méthode de construction unique de l'objet d'accès à l'API
     * @return instance unique de Retrofit
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * Méthode de récupération de l'objet de conversion en json
     */
    public static Gson getGson() {
        return gson;
    }
}