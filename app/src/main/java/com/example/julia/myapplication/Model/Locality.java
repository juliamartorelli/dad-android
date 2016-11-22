package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locality {

    @Expose
    @SerializedName("Id")
    private String id;

    @Expose
    @SerializedName("Nome")
    private String nome;

    @Expose
    @SerializedName("Descricao")
    private String descricao;

    @Expose
    @SerializedName("Logradouro")
    private String logradouro;

    @Expose
    @SerializedName("Numero")
    private String numero;

    @Expose
    @SerializedName("Complemento")
    private String complemento;

    @Expose
    @SerializedName("Cep")
    private String cep;

    @Expose
    @SerializedName("Bairro")
    private String bairro;

    @Expose
    @SerializedName("Cidade")
    private String cidade;

    @Expose
    @SerializedName("Estado")
    private String estado;

    @Expose
    @SerializedName("Pais")
    private String pais;

    @Expose
    @SerializedName("Latitude")
    private String latitude;

    @Expose
    @SerializedName("Longitude")
    private String longitude;

    @Expose
    @SerializedName("Ativo")
    private String ativo;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}