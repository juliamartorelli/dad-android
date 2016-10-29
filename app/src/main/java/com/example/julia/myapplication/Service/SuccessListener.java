package com.example.julia.myapplication.Service;

import com.android.volley.Response;

public abstract class SuccessListener<T> implements Response.Listener<T> {

    @Override
    public void onResponse(T response) {
        this.onSuccess(response);
    }

    abstract public void onSuccess(T response);
}