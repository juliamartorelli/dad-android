package com.example.julia.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;



public class SignUpUserActivity extends Activity {

    @BindView(R.id.name)
    TextView textViewName;

    @BindView(R.id.cpf)
    TextView textViewCPF;

    @BindView(R.id.phone)
    TextView textViewPhone;

    @BindView(R.id.celphone)
    TextView textViewCelphone;

    @BindView(R.id.email)
    TextView textViewEmail;

    @BindView(R.id.login)
    TextView textViewLogin;

    @BindView(R.id.password)
    TextView textViewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);
        ButterKnife.bind(this);

        final Bundle bundle = getIntent().getExtras();

    }

}
