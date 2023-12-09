package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class City {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private Coord coord;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }
}
