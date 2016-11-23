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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLocalityActivity extends Activity implements View.OnClickListener{

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

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.button_delete)
    Button buttonDelete;

    private ArrayList<Locality> locals;
    private ArrayList<String> localsString;
    private Locality localSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_locality);
        ButterKnife.bind(this);

        localSelected = new Locality();

        localsString = new ArrayList<>();
        localsString.add("Selecione a localidade");

        getLocalities();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    localSelected = locals.get(i-1);

                    textViewName.setText(localSelected.getNome());
                    textViewDescription.setText(localSelected.getDescricao());
                    textViewLogradouro.setText(localSelected.getLogradouro());
                    textViewNumber.setText(String.valueOf(Double.valueOf(localSelected.getNumero()).intValue()));
                    complement.setText(localSelected.getComplemento());
                    cep.setText(localSelected.getCep());
                    neighbourhood.setText(localSelected.getBairro());
                    city.setText(localSelected.getCidade());
                    state.setText(localSelected.getEstado());
                    country.setText(localSelected.getPais());
                } else {
                    localSelected.setId(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
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

                ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(EditLocalityActivity.this, android.R.layout.simple_spinner_item, localsString);
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
                            Intent intent = new Intent(EditLocalityActivity.this, MenuDrawerActivity.class);
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
                if (localSelected.getId() != null) {

                    String idLocality = localSelected.getId();

                    localSelected = new Locality(
                            textViewName.getText().toString(),
                            textViewDescription.getText().toString(),
                            textViewLogradouro.getText().toString(),
                            String.valueOf(Double.valueOf(textViewNumber.getText().toString()).intValue()),
                            complement.getText().toString(),
                            cep.getText().toString(),
                            neighbourhood.getText().toString(),
                            city.getText().toString(),
                            state.getText().toString(),
                            country.getText().toString());

                    localSelected.setId(String.valueOf(Double.valueOf(idLocality).intValue()));

                    Client.getInstance().editLocality(localSelected, new SuccessListener<Locality>() {
                        @Override
                        public void onSuccess(Locality response) {
                            showSignUpDialog("Sucesso", "Edição feita com sucesso! :)", true);
                        }
                    }, new ErrorListener() {
                        @Override
                        public void onError(RestError restError) {
                            showSignUpDialog("Oops...", "Houve um problema ao editar. Tente novamente em instantes.", false);
                        }
                    });
                }
                break;
            case R.id.button_delete:
                if (localSelected.getId() != null) {
                    Client.getInstance().deleteLocality(localSelected, new SuccessListener<Locality>() {
                        @Override
                        public void onSuccess(Locality response) {
                            showSignUpDialog("Sucesso", "Localidade desativada com sucesso! :)", true);
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


