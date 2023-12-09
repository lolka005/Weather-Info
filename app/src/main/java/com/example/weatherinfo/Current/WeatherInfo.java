package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class WeatherInfo
{
    @Expose
    private String code;
    @Expose
    private String message;
    @Expose
    private Integer cnt;
    @Expose
    private List<ListEl> list = new ArrayList<>();;
    @Expose
    private City city;
    @Expose
    private String country;
    @Expose
    private Integer population;
    @Expose
    private Integer timezone;
    @Expose
    private Integer sunrise;
    @Expose
    private Integer sunset;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public List<ListEl> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }
}
