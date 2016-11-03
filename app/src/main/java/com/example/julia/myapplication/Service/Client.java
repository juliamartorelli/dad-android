package com.example.julia.myapplication.Service;

import android.content.Context;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.julia.myapplication.Base.CustomApplication;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.BuildConfig;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Model.User;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;


//Classe de chamadas do cliente a API
public class Client {

    private static Client instance;
    private Context context;

    private Client() {

        this.context = CustomApplication.getInstance();
    }

    public static final Client getInstance() {

        if (instance == null) {
            instance = new Client();
            instance.setup();
        }
        return instance;
    }

    private void setup() {

        if (Preferences.getInstance().hasUserPreferences()) {
            User user = Preferences.getInstance().getCurrentUser();
            GenericRequest.setAuthToken(user.getAuthorization());
        }
    }

    public <T> void request(final Type type,
                            final int method,
                            final String resource,
                            final Object requestBody,
                            final Response.Listener<T> successListener,
                            final Response.ErrorListener errorListener) {

        String url = BuildConfig.BASE_URL;
        request(url, type, method, resource, requestBody, successListener, errorListener);
    }

    public <T> void request(final String url,
                            final Type type,
                            final int method,
                            final String resource,
                            final Object requestBody,
                            final Response.Listener<T> successListener,
                            final Response.ErrorListener errorListener) {

        RequestQueue queue = Volley.newRequestQueue(context);

        final String finalUrl = resource != null && !resource.isEmpty() ? url + resource : url;

        String requestBodyString = new Gson().toJson(requestBody).replace("\\", "");
        GenericRequest request = new GenericRequest<>(type, method, finalUrl,
                requestBodyString, successListener, errorListener);

        //request.setShouldCache(false);
        queue.add(request);
    }

    public void login(User user,
                            final SuccessListener<String> successListener,
                            final Response.ErrorListener errorListener) {

        user.setAuthorization(new String(Base64.encode((user.getEmail() + ":" + user.getPassword()).getBytes(), 0)).replace("\n",""));
        Preferences.getInstance().clear();
        Preferences.getInstance().setUserPreferences(user);

        final String resources = "/Autenticacao";
        request(String.class, Request.Method.GET, resources, null, new Response.Listener<String>() {

            @Override
            public void onResponse(String user) {
                successListener.onSuccess(user);
            }

        }, errorListener);
    }

    public void events(final SuccessListener<Object> successListener,
                       final Response.ErrorListener errorListener) {
        final String resources = "/Evento";
        request(Object.class, Request.Method.GET, resources, null, successListener, errorListener);
    }
}