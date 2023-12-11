package com.example.weatherinfo.Main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.weatherinfo.Current.Main;
import com.example.weatherinfo.Current.ListEl;
import com.example.weatherinfo.Current.Weather;
import com.example.weatherinfo.Current.WeatherInfo;
import com.example.weatherinfo.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    private static final String APIKEY = "1fbe53db893ccad351a9e881dedd13d9";
    private static final String UNITS = "metric";
    private static final String BASE_URL = "https://api.openweathermap.org/data/";
    private static final Integer CNT = 40;
    private String LAN;
    private Double LAT;
    private Double LON;
    private String Place;
    private LocationRequest locationRequest;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView place, temp, temp_like, humidity, wind_speed;
    private ImageView imageView;
    private ListView lv;
    private MyAdapter arad;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        imageView = findViewById(R.id.image);
        place = findViewById(R.id.place_name);
        temp = findViewById(R.id.temp);
        temp_like = findViewById(R.id.temp_feellsLike);
        wind_speed = findViewById(R.id.wind_speed);
        humidity = findViewById(R.id.humidity);
        lv = findViewById(R.id.lv);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        LAN = Locale.getDefault().getLanguage();
        context = this;
        getCurrentLocation();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isGPSEnabled()) {
                if (ActivityCompat.checkSelfPermission(App.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.getFusedLocationProviderClient(App.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    LocationServices.getFusedLocationProviderClient(App.this)
                                            .removeLocationUpdates(this);
                                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                                        int index = locationResult.getLocations().size() - 1;
                                        LAT = locationResult.getLocations().get(index).getLatitude();
                                        LON = locationResult.getLocations().get(index).getLongitude();
                                        LoadAPI();
                                    }
                                }
                            }, Looper.getMainLooper());
                }
                else
                {
                    LocationAccess();
                }
            } else
                {
                    GPSAccess();
                }
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null)
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }

    private void LoadAPI()
    {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        API client = retrofit.create(API.class);
        Call<WeatherInfo> call = client.getWeatherInfo(LAT, LON,APIKEY,UNITS,LAN);
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                WeatherInfo weatherInfo = response.body();
                Place = weatherInfo.getCity().getName();
                List<ListEl> myList = weatherInfo.getList();
                Main mainCur = myList.get(0).getMain();
                List<Weather> weatherList = myList.get(0).getWeather();
                Weather weather = weatherList.get(0);
                switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        imageView.setImageDrawable(getPictureBlackTheme(weather.getMain()));
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        imageView.setImageDrawable(getPictureWhiteTheme(weather.getMain()));
                        break;
                    }
                place.setText(Place);
                List<ListEl> nextDays = new ArrayList<>();
                for(int i = 0; i < CNT; i++)
                {
                    ListEl el = myList.get(i);
                    String[] Date = el.getDt_text().split(" ");
                    if(Date[1].equals("12:00:00"))
                        nextDays.add(myList.get(i));
                }
                arad = new MyAdapter(nextDays, context);
                lv.setAdapter(arad);
                temp.setText(String.valueOf(Math.round(mainCur.getTemp())) + "°С");
                if(LAN.equals("ru")){
                    humidity.setText("Влажность:\n"+String.valueOf(mainCur.getHumidity() + " %"));
                    temp_like.setText("Ощущается как:\n"+ String.valueOf(Math.round(mainCur.getFeels_like())+ "°С"));
                    wind_speed.setText(String.valueOf("Скорость ветра:\n" + Math.round(myList.get(0).getWind().getSpeed()) + "м/с"));
                }
                else {
                    temp_like.setText("Feels like:\n" + String.valueOf(Math.round(mainCur.getFeels_like()) + "°С"));
                    wind_speed.setText("Wind:\n" + String.valueOf(Math.round(myList.get(0).getWind().getSpeed()) + "m/s"));
                    humidity.setText("Humidity:\n"+String.valueOf(mainCur.getHumidity() + " %"));
                }
            }
            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(App.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });

    }

    private void LocationAccess()
    {

        String strMsg, strYes, strNo, strTitle;
        if(Objects.equals(LAN, "ru"))
        {
            strTitle = "Разрешение";
            strMsg = getResources().getString(R.string.message_location_rus);
            strNo = "Нет";
            strYes = "Да";
        }
        else
        {
            strTitle = "Permission";
            strMsg = getResources().getString(R.string.message_location_eng);
            strNo = "No";
            strYes = "Yes";
        }
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        alterDialog
                .setTitle(strTitle)
                .setMessage(strMsg)
                .setView(this.getCurrentFocus())
                .setPositiveButton(strYes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                checkPermission();
                            }
                        })
                .setNegativeButton(strNo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        alterDialog.show();
    }

    private void GPSAccess()
    {
        String strMsg, strYes, strNo;
        if(Objects.equals(LAN, "ru"))
        {
            strMsg = getResources().getString(R.string.message_gps_rus);
            strNo = "Нет";
            strYes = "Да";
        }
        else
        {
            strMsg = getResources().getString(R.string.message_gps_eng);
            strNo = "No";
            strYes = "Yes";
        }
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(strMsg);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    strYes,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            turnOnGPS();
                            LocationAccess();
                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton(
                    strNo,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.show();
    }

    @Override
    public void onRefresh()
    {
        getCurrentLocation();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3500);
    }

    private Drawable getPictureWhiteTheme(String weather)
    {
        if(weather.equals("Rain")) {
            return getResources().getDrawable(R.drawable.rain);
        } else if(weather.equals("Thunderstorm"))
            return getResources().getDrawable(R.drawable.thunderstorm);
        else if(weather.equals("Drizzle"))
            return getResources().getDrawable(R.drawable.drizle);
        else if(weather.equals("Snow"))
            return getResources().getDrawable(R.drawable.snow);
        else if(weather.equals("Clear"))
            return getResources().getDrawable(R.drawable.sun);
        else if(weather.equals("Clouds"))
            return getResources().getDrawable(R.drawable.cloud);
        else
            return getResources().getDrawable(R.drawable.mist);
    }
    private Drawable getPictureBlackTheme(String weather)
    {
        if(weather.equals("Rain")) {
            return getResources().getDrawable(R.drawable.rain_black_theme);
        } else if(weather.equals("Thunderstorm"))
            return getResources().getDrawable(R.drawable.thunderstorm_black_theme);
        else if(weather.equals("Drizzle"))
            return getResources().getDrawable(R.drawable.drizle_black_theme);
        else if(weather.equals("Snow"))
            return getResources().getDrawable(R.drawable.snow_black_theme);
        else if(weather.equals("Clear"))
            return getResources().getDrawable(R.drawable.drizle_black_theme);
        else if(weather.equals("Clouds"))
            return getResources().getDrawable(R.drawable.cloud_black_theme);
        else
            return getResources().getDrawable(R.drawable.mist_black_theme);
    }
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(App.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            checkPermission();
        }
        else {
            getCurrentLocation();
        }
    }

}