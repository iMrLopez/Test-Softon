package com.marnysofton.weatherapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class City implements Serializable {

    private int id;
    private String name;
    private Coordinates coord;
    private Principal main;
    private Wind wind;
    private Clouds clouds;
    private Object rain;
    private Object snow;
    private float dt;
    private Sys sys;
    private ArrayList<Weather> weather;

    public String getName() {
        return name;
    }

    public String getTemperature() {
        return main.getTemp();
    }
}
