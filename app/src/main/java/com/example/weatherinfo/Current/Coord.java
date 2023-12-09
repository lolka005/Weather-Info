package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class Coord {
    @Expose
    private Double lat;
    @Expose
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
