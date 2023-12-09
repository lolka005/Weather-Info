package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class Wind {
    @Expose
    private Double speed;
    @Expose
    private Integer deg;
    @Expose
    private Double gust;

    public Double getSpeed() {
        return speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public Double getGust() {
        return gust;
    }
}
