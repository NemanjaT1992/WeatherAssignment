package com.example.tomasevic.weatherassignment;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class City
{
    String name;
    String temp;
    String humidity;
    String description;
    String country;

    public City(String n, String t, String h, String d, String c)
    {
        name = n;
        temp = t;
        humidity = h;
        description = d;
        country = c;
    }
    public City()
    {
        name = "";
        temp = "";
        humidity = "";
        description = "";
        country = "";
    }

    public String getName()
    {
        return name;
    }
    public String getTemp()
    {
        return temp;
    }
    public String getHumidity()
    {
        return humidity;
    }
    public String getDescription()
    {
        return description;
    }
    public String getCountry()
    {
        return country;
    }

    public void setName(String n)
    {
        name = n;
    }
    public void setTemp(String t)
    {
        temp = t;
    }
    public void setHumidity(String h)
    {
        humidity = h;
    }
    public void setDescription(String d)
    {
        description = d;
    }
    public void setCountry(String c)
    {
        country = c;
    }
}
