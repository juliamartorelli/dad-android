package com.example.julia.myapplication.Service;

import com.google.gson.annotations.Expose;

public class RestError {

    @Expose
    private String message;

    @Expose
    private String title;

    public RestError(final String title, final String message) {

        this.title = title;
        this.message = message;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(final String title) {

        this.title = title;
    }
}