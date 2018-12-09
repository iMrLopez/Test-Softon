package com.marnysofton.weatherapp.Model;

import java.io.Serializable;

public class Principal implements Serializable {

    private String temp;
    private String pressure;
    private String humidity;
    private String temp_min;
    private String temp_max;

    public String getTemp() {
        return temp;
    }
}
