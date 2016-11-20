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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListActivity extends Activity {

    @BindView(R.id.text_view_user)
    TextView textViewUser;

    @BindView(R.id.recipe_list_view)
    ListView listView;

    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        ButterKnife.bind(this);

        textViewUser.setText("Bem vindo(a)," + Preferences.getInstance().getCurrentUser().getName() + "!");

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

                ListAdapter adapter = new ListAdapter(EventListActivity.this, events);
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
                        final Intent intent = new Intent(EventListActivity.this, EventActivity.class);
                        intent.putExtra("eventId", event.getId());
                        startActivity(intent);
                    }
                });
    }

}

