package com.example.coach.presenter;

import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;

public class HistoPresenter {
    private IHistoView vue;

    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    }

    /**
     * Demande le chargement des profils à l'API
     */
    public void chargerProfils() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if (result != null) {
                    Collections.sort(result, (p1, p2) -> {
                        if (p1.getDateMesure() == null || p2.getDateMesure() == null) {
                            return 0;
                        }
                        // Tri du plus récent au plus ancien
                        return p2.getDateMesure().compareTo(p1.getDateMesure());
                    });

                    vue.afficherListe(result);
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("Échec du chargement des profils");
            }
        });
    }

    /**
     * @param profil Le profil à supprimer
     * @param callback Lien vers l'adapter pour la mise à jour graphique
     */
    public void supprProfil(Profil profil, ICallbackApi<Void> callback) {
        String profilJson = new Gson().toJson(profil);

        HelperApi.call(HelperApi.getApi().supprProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                // Si l'API renvoie 1, la suppression est OK
                if (result != null && result == 1) {
                    vue.afficherMessage("profil supprimé");
                    callback.onSuccess(null);
                } else {
                    vue.afficherMessage("échec suppression profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("erreur lors de la suppression");
            }
        });
    }

    public void transfertProfil(Profil profil) {
        vue.transfertProfil(profil); //
    }
}