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
}
