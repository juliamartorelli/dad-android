package com.example.julia.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Model.Locality;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditEventActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.spinner_event)
    Spinner spinnerEvent;

    @BindView(R.id.name)
    EditText textViewName;

    @BindView(R.id.date)
    EditText textViewDate;

    @BindView(R.id.time)
    EditText textViewTime;

    @BindView(R.id.price)
    EditText textViewPrice;

    @BindView(R.id.locality)
    Spinner spinner;

    @BindView(R.id.image)
    EditText textViewImage;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.button_delete)
    Button buttonDelete;

    private ArrayList<Locality> locals;
    private ArrayList<Event> events;
    private Event eventSelected;
    private ArrayList<String> localsString;
    private ArrayList<String> eventsString;
    private int localId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        ButterKnife.bind(this);

        eventSelected = new Event();

        eventsString = new ArrayList<>();
        eventsString.add("Selecione o evento");

        localsString = new ArrayList<>();
        localsString.add("Localidade");

        getEvents();
        getLocalities();

        spinnerEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    eventSelected = events.get(i-1);

                    textViewName.setText(eventSelected.getName());

                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date dateEvent = format.parse(eventSelected.getDate());
                        SimpleDateFormat normalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String normalDate = normalFormat.format(dateEvent).toString();
                        textViewDate.setText(normalDate.split(" ")[0]);
                        textViewTime.setText(normalDate.split(" ")[1]);
                    } catch (ParseException e) { e.printStackTrace(); }

                    textViewPrice.setText(eventSelected.getPrice().replace('.',',')+"0");
                    textViewImage.setText(eventSelected.getUrlImage());
                    for(Locality local : locals) {
                        int idLocal = Double.valueOf(local.getId()).intValue();
                        if (idLocal == eventSelected.getIdLocality()) {
                            spinner.setSelection(locals.indexOf(local)+1);
                            break;
                        }
                    }
                } else {
                    eventSelected.setId(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    Double value = Double.valueOf(locals.get(i-1).getId());
                    localId = value.intValue();
                } else {
                    localId = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    private void getEvents() {
        Client.getInstance().events(new SuccessListener<ArrayList<Event>>() {
            @Override
            public void onSuccess(ArrayList<Event> response) {

                Gson gson = new Gson();
                events = new ArrayList<>();

                for (Object o : response) {
                    JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
                    Event event = new Gson().fromJson(jsonObject, Event.class);
                    if (event.getActive() == "true") {
                        events.add(event);
                        eventsString.add(event.getName());
                    }
                }

                ArrayAdapter<String> adapterEvents = new ArrayAdapter<>(EditEventActivity.this,android.R.layout.simple_spinner_item, eventsString);
                spinnerEvent.setAdapter(adapterEvents);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {

            }
        });
    }

    private void getLocalities() {
        Client.getInstance().localities(new SuccessListener<ArrayList<Locality>>() {
            @Override
            public void onSuccess(ArrayList<Locality> response) {

                Gson gson = new Gson();
                locals = new ArrayList<>();

                for (Object o : response) {
                    JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
                    Locality local = new Gson().fromJson(jsonObject, Locality.class);
                    locals.add(local);
                    localsString.add(local.getNome());
                }

                ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(EditEventActivity.this, android.R.layout.simple_spinner_item, localsString);
                spinner.setAdapter(adapterCategory);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) { }
        });
    }

    private void showSignUpDialog(String title, String description, final boolean success) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (success) {
                            Intent intent = new Intent(EditEventActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (eventSelected.getId() != null) {
                    int idEvento = eventSelected.getId();
                    try {
                        eventSelected = new Event(
                                textViewName.getText().toString(),
                                textViewDate.getText().toString(),
                                textViewTime.getText().toString(),
                                textViewPrice.getText().toString(),
                                localId,
                                textViewImage.getText().toString());

                        eventSelected.setId(idEvento);

                        Client.getInstance().editEvent(eventSelected, new SuccessListener<Event>() {
                            @Override
                            public void onSuccess(Event response) {
                                showSignUpDialog("Sucesso", "Edição feita com sucesso! :)", true);
                            }
                        }, new ErrorListener() {
                            @Override
                            public void onError(RestError restError) {
                                showSignUpDialog("Oops...", "Houve um problema ao editar. Tente novamente em instantes.", false);
                            }
                        });
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.button_delete:
                if (eventSelected.getId() != null) {
                    Client.getInstance().deleteEvent(eventSelected, new SuccessListener<Event>() {
                        @Override
                        public void onSuccess(Event response) {
                            showSignUpDialog("Sucesso", "Evento desativado com sucesso! :)", true);
                        }
                    }, new ErrorListener() {
                        @Override
                        public void onError(RestError restError) {
                            showSignUpDialog("Oops...", "Houve um problema ao desativar. Tente novamente em instantes.", false);
                        }
                    });
                }
                break;
        }
    }
}


