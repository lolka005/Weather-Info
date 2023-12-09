package com.example.weatherinfo.Current;

import com.google.gson.annotations.Expose;

public class Sys {
    @Expose
    private String pod;

    public String getPod() {
        return pod;
    }
}
