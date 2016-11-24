package com.example.julia.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.julia.myapplication.Base.Preferences;
import com.example.julia.myapplication.Model.User;
import com.example.julia.myapplication.Service.Client;
import com.example.julia.myapplication.Service.ErrorListener;
import com.example.julia.myapplication.Service.RestError;
import com.example.julia.myapplication.Service.SuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends Activity {

    @BindView(R.id.name)
    TextView textViewName;

    @BindView(R.id.email)
    TextView textViewEmail;

    @BindView(R.id.login)
    TextView textViewLogin;

    @BindView(R.id.password)
    TextView textViewPassword;

    @BindView(R.id.button_edit)
    Button buttonEdit;

    @BindView(R.id.button_delete)
    Button buttonDelete;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        Client.getInstance().user(
                Preferences.getInstance().getCurrentUser().getId(),
                new SuccessListener<User>() {
            @Override
            public void onSuccess(User response) {

                user = response;
                textViewName.setText(user.getName());
                textViewEmail.setText(user.getEmail());
                textViewLogin.setText(user.getLogin());
                textViewPassword.setText(user.getPassword());
            }
        }, new ErrorListener() {
            @Override
            public void onError(RestError restError) { }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = new User(
                        textViewName.getText().toString(),
                        textViewEmail.getText().toString(),
                        textViewLogin.getText().toString(),
                        textViewPassword.getText().toString());
                user.setId(Preferences.getInstance().getCurrentUser().getId());

                Client.getInstance().editUser(user, new SuccessListener<User>() {
                    @Override
                    public void onSuccess(User response) {
                        showSignUpDialog("Sucesso", "Edição feita com sucesso! :)", true, false);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onError(RestError restError) {
                        showSignUpDialog("Oops...", "Houve um problema ao editar. Tente novamente em instantes.", false, false);
                    }
                });
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Client.getInstance().deleteUser(Preferences.getInstance().getCurrentUser().getId(), new SuccessListener<User>() {
                    @Override
                    public void onSuccess(User response) {
                        showSignUpDialog("Sucesso", "Conta desativada com sucesso! :)", true, true);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onError(RestError restError) {
                        showSignUpDialog("Oops...", "Houve um problema ao desativar. Tente novamente em instantes.", false, true);
                    }
                });
            }
        });
    }

    private void showSignUpDialog(final String title,
                                  final String description,
                                  final boolean success,
                                  final boolean delete) {

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (delete) {
                            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Preferences.getInstance().clear();
                        } else {
                            Intent intent = new Intent(EditProfileActivity.this, MenuDrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .show();
    }
}
