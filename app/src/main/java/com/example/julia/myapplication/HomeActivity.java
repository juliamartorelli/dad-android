package com.example.julia.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.gson.JsonObject;
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

        textViewUser.setText("Bem vindo(a)," + Preferences.getInstance().getCurrentUser().getName() + "!");

        listView = (ListView) findViewById(R.id.recipe_list_view);

        Client.getInstance().events(new SuccessListener<ArrayList<Event>>() {
            @Override
            public void onSuccess(ArrayList<Event> response) {

                Gson gson = new Gson();
                events = new ArrayList<Event>();

                for (Object o : response) {
                    JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
                    Event event = new Gson().fromJson(jsonObject, Event.class);
                    events.add(event);
                }

                ListAdapter adapter = new ListAdapter(HomeActivity.this, events);
                listView.setAdapter(adapter);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        final Event event = events.get(position);
                        final Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                        intent.putExtra("eventId", event.getId());
                        startActivity(intent);
                    }
                });
    }

}

