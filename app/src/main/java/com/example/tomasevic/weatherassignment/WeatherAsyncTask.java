package com.example.tomasevic.weatherassignment;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class WeatherAsyncTask extends AsyncTask
{
    JSONParser parser = null;
    City city = null;
    @Override
    protected ArrayList<City> doInBackground(Object[] params)
    {
        String data = (new WeatherHttpClient()).getWeatherData(params[0].toString());

        try
        {
            parser = new JSONParser(data);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

//        if(parser.getCity() != null)
//        return parser.getCity();
        if(parser.getCitiesList() != null)
            return parser.getCitiesList();

        return null;
    }
}
