package com.example.julia.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.example.julia.myapplication.Adapter.ListAdapter;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity {

    @BindView(R.id.textView_user)
    TextView textViewUser;
    private List<Event> events;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Preferences pr = new Preferences();
        textViewUser.setText("Bem vindo(a), Maria das Neves"/* + pr.getCurrentUser().getName() + "!"*/);

        listView = (ListView) findViewById(R.id.recipe_list_view);

        Client.getInstance().events(new SuccessListener<Object>() {
            @Override
            public void onSuccess(Object response) {

                String responseString = response.toString();
                Gson gson = new Gson();
                events = (List<Event>)gson.fromJson(responseString, new TypeToken<List<Event>>(){}.getType());

                ListAdapter adapter = new ListAdapter(HomeActivity.this, events);
                listView.setAdapter(adapter);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {

            }
        });
    }

}

