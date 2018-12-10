package com.marnysofton.weatherapp.Model;

import java.io.Serializable;

public class Sys implements Serializable {
    private String type;
    private String id;
    private String message;
    private String country;
    private String sunrise;
    private String sunset;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
