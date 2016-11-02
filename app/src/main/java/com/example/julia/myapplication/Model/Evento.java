package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evento {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("Nome")
    private String nome;

    @Expose
    @SerializedName("UrlImagem")
    private String urlImagem;

    @Expose
    @SerializedName("DataEvento")
    private String data;

    @Expose
    @SerializedName("Ativo")
    private String ativo;

    @Expose
    @SerializedName("Quantidade")
    private String quantidade;

    @Expose
    @SerializedName("Valor")
    private String valor;

    @Expose
    @SerializedName("Localidade")
    private Localidade localidade;

    public Evento (String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }
}
