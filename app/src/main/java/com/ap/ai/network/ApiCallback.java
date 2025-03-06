package com.ap.ai.network;

public interface ApiCallback<T> {
    void onSuccess(T response);
    void onFailure(String message);
} 