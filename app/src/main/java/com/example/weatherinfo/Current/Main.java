package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class Main {
    @Expose
    private Double temp;
    @Expose
    private Double feels_like;
    @Expose
    private Double temp_min;
    @Expose
    private Double temp_max;
    @Expose
    private Integer pressure;
    @Expose
    private Integer sea_level;
    @Expose
    private Integer grnd_level;
    @Expose
    private Integer humidity;
    @Expose
    private Double temp_kf;

    public Double getTemp() {
        return temp;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getSea_level() {
        return sea_level;
    }

    public Integer getGrnd_level() {
        return grnd_level;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getTemp_kf() {
        return temp_kf;
    }
}
