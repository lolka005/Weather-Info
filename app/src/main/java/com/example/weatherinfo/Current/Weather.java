package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class Weather
{
    @Expose
    private Integer id;
    @Expose
    private String main;
    @Expose
    private String description;
    @Expose
    private String icon;

    public Integer getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
