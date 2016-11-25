package com.example.julia.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class EventListFragment extends Fragment {

    @BindView(R.id.list_view)
    ListView listView;

    private List<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.activity_events_list, parent, false);
        ButterKnife.bind(this, fragmentView);

        ((AppCompatActivity)getActivity())
                .getSupportActionBar()
                .setTitle("Eventos disponíveis");

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

                ListAdapter adapter = new ListAdapter(getActivity(), events);
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
                        final Intent intent = new Intent(getActivity(), EventActivity.class);
                        intent.putExtra("eventId", event.getId());
                        startActivity(intent);
                    }
                });

        return fragmentView;
    }
}

