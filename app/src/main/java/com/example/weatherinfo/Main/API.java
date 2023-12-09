package com.example.weatherinfo.Main;

import com.example.weatherinfo.Current.WeatherInfo;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API
{
    @GET("2.5/forecast")
    Call<WeatherInfo> getWeatherInfo(@Query("lat") Double lat, @Query("lon") Double lon, @Query("appid") String api, @Query("units") String units, @Query("lang") String lang);
}
