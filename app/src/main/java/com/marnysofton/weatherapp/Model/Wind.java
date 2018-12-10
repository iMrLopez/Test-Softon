package com.marnysofton.weatherapp.Model;

import java.io.Serializable;

public class Wind implements Serializable {

    private String speed;
    private String deg;

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
