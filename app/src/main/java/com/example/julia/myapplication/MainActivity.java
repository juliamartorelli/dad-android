package com.example.julia.myapplication;

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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_email)
    private EditText editTextEmail;

    @BindView(R.id.edit_text_password)
    private EditText editTextPassword;

    @BindView(R.id.button)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {

        final User user = new User(editTextEmail.getText().toString(), editTextPassword.getText().toString());

        Client.getInstance().login(user, new SuccessListener<User>() {
            @Override
            public void onSuccess(User response) {
                //TODO: activity home
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) {
                //TODO: qual vai ser a mensagem?
            }
        }); 
    }
}

