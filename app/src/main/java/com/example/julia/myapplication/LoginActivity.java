package com.example.julia.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.julia.myapplication.Base.Preferences;
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

    @BindView(R.id.imageView1)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        final User user = new User(editTextEmail.getText().toString(), editTextPassword.getText().toString());

        Client.getInstance().login(user, new SuccessListener<User>() {
            @Override
            public void onSuccess(User response) {
                if (!response.getActive()) {
                    showLoginDialog("Oops...", "Houve um problema ao realizar o login. Tente novamente em instantes.");
                } else {
                    Intent it = new Intent(LoginActivity.this, MenuDrawerActivity.class);
                    startActivity(it);
                }
        }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {
            }
        });
    }

    private void showLoginDialog(final String title,
                                  final String description) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}



