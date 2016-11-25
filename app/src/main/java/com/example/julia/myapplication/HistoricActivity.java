package com.example.julia.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.julia.myapplication.Adapter.ListAdapter;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Model.Ticket;
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

    @BindView(R.id.list_view)
    ListView listView;

    private List<Event> events;
    private List<Ticket> tickets;
    private ProgressDialog pd;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic);
        ButterKnife.bind(this);

        events = new ArrayList<>();
        tickets = new ArrayList<>();

        pd = ProgressDialog.show(this, "Carregando ingressos", "");
        Client.getInstance().tickets(
                Preferences.getInstance().getCurrentUser().getId(),
                new SuccessListener<ArrayList<Ticket>>() {
            @Override
            public void onSuccess(ArrayList<Ticket> response) {

                Gson gson = new Gson();

                for (Object o : response) {
                    JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
                    Ticket ticket = new Gson().fromJson(jsonObject, Ticket.class);
                    tickets.add(ticket);
                }

                for(Ticket ticket : tickets) {
                    getEvent(ticket.getIdEvent());
                }
                pd.dismiss();
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {
                pd.dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Event event = events.get(position);
                final Intent intent = new Intent(HistoricActivity.this, EventActivity.class);
                intent.putExtra("eventId", event.getId());
                intent.putExtra("ingresso", true);
                startActivity(intent);
            }
        });
    }

    private void getEvent(int idEvent) {

        Client.getInstance().event(idEvent, new SuccessListener<Event>() {
            @Override
            public void onSuccess(Event response) {

                if(response.getActive() == "true") {
                    events.add(response);
                }
                adapter = new ListAdapter(getApplicationContext(), events);
                listView.setAdapter(adapter);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {
                pd.dismiss();
            }
        });
    }
}
