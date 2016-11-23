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
import com.example.julia.myapplication.Model.Locality;
import com.example.julia.myapplication.Model.Ticket;
import com.example.julia.myapplication.Model.User;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
                            final SuccessListener<User> successListener,
                            final Response.ErrorListener errorListener) {

        user.setAuthorization(new String(Base64.encode((user.getEmail() + ":" + user.getPassword()).getBytes(), 0)).replace("\n",""));
        Preferences.getInstance().clear();
        Preferences.getInstance().setUserPreferences(user);

        final String resources = "/Autenticacao";
        request(User.class, Request.Method.GET, resources, null, new Response.Listener<User>() {

            @Override
            public void onResponse(User response) {
                successListener.onSuccess(response);
                Preferences.getInstance().setUserPreferences(response);
            }

        }, errorListener);
    }

    public void createUser(User user,
                                final SuccessListener<User> successListener,
                                final Response.ErrorListener errorListener) {

        final String resources = "/Usuario";
        request(User.class, Request.Method.POST, resources, user, new Response.Listener<User>() {

            @Override
            public void onResponse(User response) {
                successListener.onSuccess(response);
                Preferences.getInstance().setUserPreferences(response);
            }

        }, errorListener);
    }

    public void createEvent(Event event,
                                 final SuccessListener<Event> successListener,
                                 final Response.ErrorListener errorListener) {

        final String resources = "/Evento";
        request(Event.class, Request.Method.POST, resources, event, successListener, errorListener);
    }

    public void editEvent(Event event,
                            final SuccessListener<Event> successListener,
                            final Response.ErrorListener errorListener) {

        final String resources = "/Evento/" + event.getId();
        request(Event.class, Request.Method.PUT, resources, event, successListener, errorListener);
    }


    public void editLocality(Locality local,
                          final SuccessListener<Locality> successListener,
                          final Response.ErrorListener errorListener) {

        final String resources = "/Localidade/" + local.getId();
        request(Locality.class, Request.Method.PUT, resources, local, successListener, errorListener);
    }

    public void deleteEvent(Event event,
                          final SuccessListener<Event> successListener,
                          final Response.ErrorListener errorListener) {

        final String resources = "/Evento/" + event.getId();
        request(Event.class, Request.Method.DELETE, resources, event, successListener, errorListener);
    }

    public void deleteLocality(Locality local,
                            final SuccessListener<Locality> successListener,
                            final Response.ErrorListener errorListener) {

        final String resources = "/Localidade/" + Double.valueOf(local.getId()).intValue();
        request(Locality.class, Request.Method.DELETE, resources, local, successListener, errorListener);
    }

    public void createLocality(Locality local,
                            final SuccessListener<Locality> successListener,
                            final Response.ErrorListener errorListener) {

        final String resources = "/Localidade";
        request(Locality.class, Request.Method.POST, resources, local, successListener, errorListener);
    }

    public void events(final SuccessListener<ArrayList<Event>> successListener,
                       final Response.ErrorListener errorListener) {

        final String resources = "/Evento";
        request(ArrayList.class, Request.Method.GET, resources, null, successListener, errorListener);
    }

    public void localities(final SuccessListener<ArrayList<Locality>> successListener,
                       final Response.ErrorListener errorListener) {

        final String resources = "/Localidade";
        request(ArrayList.class, Request.Method.GET, resources, null, successListener, errorListener);
    }

    public void event(final int id,
                      final SuccessListener<Event> successListener,
                      final Response.ErrorListener errorListener) {

        final String resources = "/Evento/" + id;
        request(Event.class, Request.Method.GET, resources, null, successListener, errorListener);
    }

    public void locality(final int id,
                      final SuccessListener<Locality> successListener,
                      final Response.ErrorListener errorListener) {

        final String resources = "/Localidade/" + id;
        request(Locality.class, Request.Method.GET, resources, null, successListener, errorListener);
    }

    public void buyTicket(Ticket ticket,
                         final SuccessListener<Ticket> successListener,
                         final Response.ErrorListener errorListener) {

        final String resources = "/Ingresso";
        request(Ticket.class, Request.Method.POST, resources, ticket, successListener, errorListener);
    }
}