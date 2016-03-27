package com.example.tomasevic.weatherassignment;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class JSONParser
{
    City result;
    ArrayList<City> resultList = new ArrayList<>();

    public JSONParser(String data) throws JSONException
    {
        JSONObject jsonData = new JSONObject(data);

        if(!jsonData.has("list"))
        {
            String name = getString("name", jsonData);
            JSONObject main = getObject("main", jsonData);
            String temp = getString("temp", main);
            String humidity = getString("humidity", main);

            JSONArray weatherArray = jsonData.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String desctiption = getString("description", weatherObject);

            JSONObject sys = getObject("sys", jsonData);
            String country = getString("country", sys);
            String cityOwmIdString = getString("id", jsonData);

            int tempInCelsius = (int) (Double.parseDouble(temp) - 273.15);

            result = new City(name, String.valueOf(tempInCelsius) + (char) 0x00B0 + "C", humidity, desctiption, country, cityOwmIdString);
            resultList.add(result);
        }
        else
        {
            JSONArray listData = jsonData.getJSONArray("list");
            for(int i=0; i<listData.length(); ++i)
            {
                City tmpResult;
                String name = getString("name", listData.getJSONObject(i));
                JSONObject main = getObject("main", listData.getJSONObject(i));
                String temp = getString("temp", main);
                String humidity = getString("humidity", main);

                JSONArray weatherArray = listData.getJSONObject(i).getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String desctiption = getString("description", weatherObject);

                JSONObject sys = getObject("sys", listData.getJSONObject(i));
                String country = getString("country", sys);
                String cityOwmIdString = getString("id", listData.getJSONObject(i));

                int tempInCelsius = (int) (Double.parseDouble(temp) - 273.15);

                tmpResult = new City(name, String.valueOf(tempInCelsius) + (char) 0x00B0 + "C", humidity, desctiption, country, cityOwmIdString);
                resultList.add(tmpResult);
            }
        }

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
    public ArrayList<City> getCitiesList()
    {
        return resultList;
    }
}
