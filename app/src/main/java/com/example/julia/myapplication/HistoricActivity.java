package com.example.julia.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.julia.myapplication.Adapter.ListAdapter;
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

public class HistoricActivity extends Activity {

    @BindView(R.id.recipe_list_view)
    ListView listView;

    private List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        ButterKnife.bind(this);

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

                ListAdapter adapter = new ListAdapter(getApplicationContext(), events);
                listView.setAdapter(adapter);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {

            }
        });
    }
}
