package com.example.julia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email)
    EditText editTextEmail;

    @BindView(R.id.edit_text_password)
    EditText editTextPassword;

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.button_create_user)
    Button buttonCreateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginActivity.this, SignUpUserActivity.class);
                startActivity(it);
            }
        });
    }

    private void login() {

        final User user = new User("julia", "12345");

        Client.getInstance().login(user, new SuccessListener<User>() {
            @Override
            public void onSuccess(User response) {
                Intent it = new Intent(LoginActivity.this, MenuDrawerActivity.class);
                startActivity(it);
        }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) { }
        });
    }
}



