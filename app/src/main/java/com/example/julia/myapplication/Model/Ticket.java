package com.example.julia.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticket {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("Ativo")
    private Boolean active;

    @Expose
    @SerializedName("Nome")
    private String name;

    @Expose
    @SerializedName("IdCliente")
    private Integer idClient;

    @Expose
    @SerializedName("IdEvento")
    private Integer idEvent;

    public Ticket(Integer id, Boolean active, String name, Integer idClient, Integer idEvent) {

        this.id = id;
        this.active = active;
        this.name = name;
        this.idClient = idClient;
        this.idEvent = idEvent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }
}
