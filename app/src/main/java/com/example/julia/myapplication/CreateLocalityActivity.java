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

public class CreateLocalityActivity extends Activity {

    @BindView(R.id.name)
    EditText textViewName;

    @BindView(R.id.description)
    EditText textViewDescription;

    @BindView(R.id.logradouro)
    EditText textViewLogradouro;

    @BindView(R.id.number)
    EditText textViewNumber;

    @BindView(R.id.complement)
    EditText complement;

    @BindView(R.id.cep)
    EditText cep;

    @BindView(R.id.neighbourhood)
    EditText neighbourhood;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.state)
    EditText state;

    @BindView(R.id.country)
    EditText country;

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_locality);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Locality local = new Locality(
                        textViewName.getText().toString(),
                        textViewDescription.getText().toString(),
                        textViewLogradouro.getText().toString(),
                        textViewNumber.getText().toString(),
                        complement.getText().toString(),
                        cep.getText().toString(),
                        neighbourhood.getText().toString(),
                        city.getText().toString(),
                        state.getText().toString(),
                        country.getText().toString());


                        Client.getInstance().createLocality(local, new SuccessListener<Locality>() {
                    @Override
                    public void onSuccess(Locality response) {
                        showSignUpDialog("Sucesso", "Cadastro feito com sucesso! :)", true);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onError(RestError restError) {
                        showSignUpDialog("Oops...", "Houve um problema ao cadastrar. Tente novamente em instantes.", false);
                    }
                });
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
                            Intent intent = new Intent(CreateLocalityActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }

}


