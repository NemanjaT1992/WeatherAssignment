package com.example.tomasevic.weatherassignment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class JSONParser
{
    City result;

    public JSONParser(String data) throws JSONException
    {
        JSONObject jsonData = new JSONObject(data);

        String name = getString("name", jsonData);
        JSONObject main = getObject("main", jsonData);
        String temp = getString("temp", main);
        String humidity = getString("humidity", main);

        JSONArray weatherArray = jsonData.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String desctiption = getString("description", weatherObject);

        JSONObject sys = getObject("sys", jsonData);
        String country = getString("country", sys);

        result = new City(name, temp, humidity, desctiption, country);

    }
    public JSONObject getObject(String name, JSONObject obj) throws JSONException
    {
        JSONObject resObj = obj.getJSONObject(name);
        return  resObj;
    }
    public String getString(String name, JSONObject obj) throws JSONException
    {
        return obj.getString(name);
    }
    public City getCity()
    {
        return result;
    }
}
