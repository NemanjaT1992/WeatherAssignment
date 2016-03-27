package com.example.tomasevic.weatherassignment;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tomasevic on 26.3.2016..
 */
public class WeatherAssignmentApplication extends Application
{
    private static WeatherAssignmentApplication instance;

    public WeatherAssignmentApplication()
    {
        instance = this;
    }
    public static Context getContext()
    {
        return instance;
    }
}
