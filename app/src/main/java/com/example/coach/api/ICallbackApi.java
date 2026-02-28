package com.example.coach.api;

/**
 * Interface générique pour gérer les retours d'API
 */
public interface ICallbackApi<T> {
    void onSuccess(T result);
    void onError();
}