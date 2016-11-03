package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event  {

    @Expose
    @SerializedName("Id")
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
    @SerializedName("IdLocalidade")
    private String idLocalidade;

    public Event(String nome) {
        this.nome = nome;
    }

    public Event() {}

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

    public String getIdLocalidade() {
        return idLocalidade;
    }

    public void setIdLocalidade(String idLocalidade) {
        this.idLocalidade = idLocalidade;
    }
}
