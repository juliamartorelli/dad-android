package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event  {

    @Expose
    @SerializedName("Id")
    private Integer id;

    @Expose
    @SerializedName("Nome")
    private String name;

    @Expose
    @SerializedName("UrlImagem")
    private String urlImage;

    @Expose
    @SerializedName("DataEvento")
    private String date;

    @Expose
    @SerializedName("Ativo")
    private String active;

    @Expose
    @SerializedName("Quantidade")
    private String quantity;

    @Expose
    @SerializedName("Valor")
    private String price;

    @Expose
    @SerializedName("IdLocalidade")
    private Integer idLocality;

    public Event(String nome) {
        this.name = nome;
    }

    public Event() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getIdLocality() {
        return idLocality;
    }

    public void setIdLocality(Integer idLocality) {
        this.idLocality = idLocality;
    }
}
