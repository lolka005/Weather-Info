package com.example.weatherinfo.Main;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherinfo.Current.ListEl;
import com.example.weatherinfo.Current.Weather;
import com.example.weatherinfo.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    Context context;
    List<ListEl> list = new ArrayList<ListEl>();

    public MyAdapter(List<ListEl> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return  list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.next_days, parent, false);
        }
        LinearLayout ll = (LinearLayout) convertView;
        TextView temp = ll.findViewById(R.id.temp_next);
        TextView date = ll.findViewById(R.id.date_next);
        ImageView imageView = ll.findViewById(R.id.image_next);
        ListEl el = list.get(position);
        List<Weather> weatherList = list.get(position).getWeather();
        Weather weather = weatherList.get(0);
        temp.setText(String.valueOf(Math.round(el.getMain().getTemp())) + "°С");
        String[] Date = el.getDt_text().split(" ");
        date.setText(Date[0]);
        switch (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                imageView.setImageDrawable(getPictureBlackTheme(weather.getMain()));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                imageView.setImageDrawable(getPictureWhiteTheme(weather.getMain()));
                break;
        }
        return convertView;
    }
    private Drawable getPictureWhiteTheme(String weather)
    {
        if(weather.equals("Rain")) {
            return context.getResources().getDrawable(R.drawable.rain);
        } else if(weather.equals("Thunderstorm"))
            return context.getResources().getDrawable(R.drawable.thunderstorm);
        else if(weather.equals("Drizzle"))
            return context.getResources().getDrawable(R.drawable.drizle);
        else if(weather.equals("Snow"))
            return context.getResources().getDrawable(R.drawable.snow);
        else if(weather.equals("Clear"))
            return context.getResources().getDrawable(R.drawable.sun);
        else if(weather.equals("Clouds"))
            return context.getResources().getDrawable(R.drawable.cloud);
        else
            return context.getResources().getDrawable(R.drawable.mist);
    }

    private Drawable getPictureBlackTheme(String weather)
    {
        if(weather.equals("Rain")) {
            return context.getResources().getDrawable(R.drawable.rain_black_theme);
        } else if(weather.equals("Thunderstorm"))
            return context.getResources().getDrawable(R.drawable.thunderstorm_black_theme);
        else if(weather.equals("Drizzle"))
            return context.getResources().getDrawable(R.drawable.drizle_black_theme);
        else if(weather.equals("Snow"))
            return context.getResources().getDrawable(R.drawable.snow_black_theme);
        else if(weather.equals("Clear"))
            return context.getResources().getDrawable(R.drawable.sun_black_theme);
        else if(weather.equals("Clouds"))
            return context.getResources().getDrawable(R.drawable.cloud_black_theme);
        else
            return context.getResources().getDrawable(R.drawable.mist_black_theme);
    }
}
