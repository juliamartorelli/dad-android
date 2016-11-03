package com.example.julia.myapplication.Service;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.User;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GenericRequest<T> extends JsonRequest<T> {

    public static final int INITIAL_TIMEOUT_MS = 5000;
    private static String authToken;
    private Response.ErrorListener errorListener;
    private Response.Listener<T> successListener;
    private Map<String, String> headers = new HashMap<>();
    private final String requestBody;
    private Type type;

    public GenericRequest(Type type, int method, String url, String requestBody,
                          final Response.Listener<T> successListener, Response.ErrorListener errorListener) {

        super(method, url, requestBody, successListener, errorListener);
        this.requestBody = requestBody;
        this.successListener = successListener;
        this.errorListener = errorListener;
        this.type = type;

        setRetryPolicy(
                new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() {

        User user = Preferences.getInstance().getCurrentUser();
        GenericRequest.setAuthToken(user.getAuthorization());
        if (user.getAuthorization() != null) {
            headers.put("Authorization", "Basic " + user.getAuthorization());
        }
        return headers;
    }

    @Override
    public String getBodyContentType() {

        return "application/json";
    }

    public static void setAuthToken(String token) {

        GenericRequest.authToken = token;
    }

    @Override
    protected void deliverResponse(T response) {
        successListener.onResponse(response);
    }

    @Override
    public Response.ErrorListener getErrorListener() {
        return this.errorListener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        if (response.statusCode >= 200 && response.statusCode < 300) {
            String data;
            try {
                data = new String(response.data, "UTF-8");
                T model = new Gson().fromJson(data, type);
                return Response.success(model, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return Response.error(new VolleyError(response));
    }
}