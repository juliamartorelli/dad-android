package com.example.julia.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SignUpUserActivity extends Activity {

    @BindView(R.id.name)
    TextView textViewName;

    @BindView(R.id.cpf)
    TextView textViewCpf;

    @BindView(R.id.email)
    TextView textViewEmail;

    @BindView(R.id.login)
    TextView textViewLogin;

    @BindView(R.id.password)
    TextView textViewPassword;

    @BindView(R.id.create_user)
    Button buttonCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);
        ButterKnife.bind(this);

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User(
                        textViewName.getText().toString(),
                        textViewCpf.getText().toString(),
                        textViewEmail.getText().toString(),
                        textViewLogin.getText().toString(),
                        textViewPassword.getText().toString());

                Client.getInstance().createUser(user, new SuccessListener<User>() {
                    @Override
                    public void onSuccess(User response) {
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
                            Intent intent = new Intent(SignUpUserActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }

}


