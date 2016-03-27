package com.example.tomasevic.weatherassignment;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class City
{
    long ID;
    String name;
    String temp;
    String humidity;
    String description;
    String country;
    String city_owm_id;

    public City(String n, String t, String h, String d, String c, String cowmid)
    {
        name = n;
        temp = t;
        humidity = h;
        description = d;
        country = c;
        city_owm_id = cowmid;
    }
    public City()
    {
        name = "";
        temp = "";
        humidity = "";
        description = "";
        country = "";
        ID = -1;
        city_owm_id = "";
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
    public long getID(){return  ID;}
    public String getCityOwmID(){return city_owm_id;}

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
    public void setID(long id){ID = id;}
    public void setCityOwmID(String id){city_owm_id = id;}
}
