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

    public int getId() {
        return id;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public Principal getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Object getRain() {
        return rain;
    }

    public Object getSnow() {
        return snow;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }
}
