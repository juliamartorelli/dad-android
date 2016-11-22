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
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.julia.myapplication.Adapter.ListAdapter;
import com.example.julia.myapplication.Model.Event;
import com.example.julia.myapplication.Model.Locality;
import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateEventActivity extends Activity {

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

    private ArrayList<Locality> locals;
    private ArrayList<String> localsString;
    private int localId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ButterKnife.bind(this);

        localsString = new ArrayList<>();
        localsString.add("Localidade");

        Client.getInstance().localities(new SuccessListener<ArrayList<Locality>>() {
            @Override
            public void onSuccess(ArrayList<Locality> response) {

                Gson gson = new Gson();
                locals = new ArrayList<Locality>();

                for (Object o : response) {
                    JsonObject jsonObject = gson.toJsonTree(o).getAsJsonObject();
                    Locality local = new Gson().fromJson(jsonObject, Locality.class);
                    locals.add(local);
                    localsString.add(local.getNome());
                }

                ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(CreateEventActivity.this,android.R.layout.simple_spinner_item, localsString);
                spinner.setAdapter(adapterCategory);
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) { }
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Event event = new Event(
                            textViewName.getText().toString(),
                            textViewDate.getText().toString(),
                            textViewTime.getText().toString(),
                            textViewPrice.getText().toString(),
                            localId,
                            textViewImage.getText().toString());

                    Client.getInstance().createEvent(event, new SuccessListener<Event>() {
                        @Override
                        public void onSuccess(Event response) {
                            showSignUpDialog("Sucesso", "Cadastro feito com sucesso! :)", true);
                        }
                    }, new ErrorListener() {
                        @Override
                        public void onError(RestError restError) {
                            showSignUpDialog("Oops...", "Houve um problema ao cadastrar. Tente novamente em instantes.", false);
                        }
                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showSignUpDialog(String title, String description, final boolean success) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (success) {
                            Intent intent = new Intent(CreateEventActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }

}


