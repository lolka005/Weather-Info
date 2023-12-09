package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class ListEl
{
    @Expose
    private Long dt;
    @Expose
    private Main main;
    @Expose
    private java.util.List<Weather> weather;
    @Expose
    private Wind wind;
    @Expose
    private Integer visibility;
    @Expose
    private Double pop;
    @Expose
    private String dt_txt;

    public Long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public Double getPop() {
        return pop;
    }

    public String getDt_text() {
        return dt_txt;
    }
}