package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("Id")
    private String id;

    @Expose
    @SerializedName("Nome")
    private String name;

    @Expose
    @SerializedName("Login")
    private String login;

    @Expose
    @SerializedName("Email")
    private String email;

    @Expose
    @SerializedName("Senha")
    private String password;

    @Expose
    @SerializedName("Cpf")
    private String cpf;

    @Expose
    @SerializedName("Telefone")
    private String telephone;

    @Expose
    @SerializedName("Celular")
    private String celphone;

    @Expose
    @SerializedName("Ativo")
    private Boolean active;

    @Expose
    @SerializedName("Administrador")
    private Boolean admin;

    private String authorization;

    public User (){}

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCelphone() {
        return celphone;
    }

    public void setCelphone(String celphone) {
        this.celphone = celphone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}