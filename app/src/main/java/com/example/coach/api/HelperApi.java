package com.example.coach.api;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperApi {
    // Constante de classe pour l'accès unique à l'API
    private static final IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

    public static IRequestApi getApi() {
        return api;
    }

    /**
     * Méthode générique pour exécuter n'importe quel appel API
     */
    public static <T> void call(Call<ResponseApi<T>> call, ICallbackApi<T> callback) {
        call.enqueue(new Callback<ResponseApi<T>>() {
            @Override
            public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResult()); // Succès
                } else {
                    Log.e("API", "Erreur: " + response.code());
                    callback.onError(); // Échec
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<T>> call, Throwable t) {
                Log.e("API", "Erreur réseau", t);
                callback.onError(); // Échec réseau
            }
        });
    }
}