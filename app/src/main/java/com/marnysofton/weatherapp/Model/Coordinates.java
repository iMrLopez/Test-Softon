package com.marnysofton.weatherapp.Model;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private String lat;
    private String lon;


    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
