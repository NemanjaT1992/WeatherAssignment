package com.example.tomasevic.weatherassignment;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class CityListItem
{
    private String name;
    private String temp;

    public CityListItem()
    {

    }

    public String getCityName()
    {
        return name;
    }
    public String getCityTemp()
    {
        return temp;
    }
    public void setCityName(String n)
    {
        name = n;
    }
    public void setCityTemp(String t)
    {
        temp = t;
    }
}
