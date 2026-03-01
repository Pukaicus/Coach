package com.example.coach.presenter;

import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IAllView;

public class MainPresenter {
    private IAllView vue;

    public MainPresenter(IAllView vue) {
        this.vue = vue;
    }

    public void purge() {
        HelperApi.call(HelperApi.getApi().purgeProfils(), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                vue.afficherMessage(result + " profils supprimés");
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec de purge");
            }
        });
    }
}