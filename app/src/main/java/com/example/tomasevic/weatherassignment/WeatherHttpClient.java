package com.example.tomasevic.weatherassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class WeatherHttpClient
{
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String APIKEY = "&APPID=376a8e8db3dbb5bb7a4d765abee08f6c";

    public WeatherHttpClient()
    {

    }
    public String getWeatherData(String name)
    {
        HttpURLConnection connection = null;
        InputStream is = null;

        try
        {
            String urlString = BASE_URL + name + APIKEY;
//            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=Belgrade&APPID=376a8e8db3dbb5bb7a4d765abee08f6c";
            connection = (HttpURLConnection) (new URL(urlString)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuffer buffer = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = null;

            while ((line = bufferedReader.readLine()) != null)
            {
                buffer.append(line + "rn");
            }

            is.close();
            connection.disconnect();
            return buffer.toString();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            } catch (Throwable t)
            {
                t.printStackTrace();
            }
            try
            {
                connection.disconnect();
            }
            catch (Throwable t)
            {

            }
        }
        return null;
    }
}
