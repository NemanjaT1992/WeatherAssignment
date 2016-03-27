package com.example.tomasevic.weatherassignment;

import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

/**
 * Created by Tomasevic on 26.3.2016..
 */
public class CitiesData
{
    private ArrayList<City> cities;
    WeatherAssignmentDBAdapter dbAdapter;

    private CitiesData()
    {
        cities = new ArrayList<>();
//        cities.add(new City("Cecina", "12", "88", "selo", "RS"));
//        cities.add(new City("Rusna", "9", "99", "selo", "RS"));
//        cities.add(new City("Babusnica", "15", "689", "grad", "RS"));
//        cities.add(new City("Meljak", "25", "10", "selo", "BG"));

        dbAdapter = new WeatherAssignmentDBAdapter(WeatherAssignmentApplication.getContext());
        dbAdapter.open();
        this.cities = dbAdapter.getAllCities();
        dbAdapter.close();
    }

    private static class SingletonHolder
    {
        public static final CitiesData instance = new CitiesData();
    }

    public static CitiesData getInstance()
    {
        return SingletonHolder.instance;
    }
    public ArrayList<City> getCities()
    {
        return cities;
    }
    public void addNewCity(City city)
    {
        cities.add(city);
        dbAdapter.open();
        long id = dbAdapter.insertCity(city);
        dbAdapter.close();
        city.setID(id);
    }
    public City getCity(int index)
    {
        return cities.get(index);
    }
    public void deleteCity(int index)
    {
        City city = cities.remove(index);
        dbAdapter.open();
        boolean success = dbAdapter.removeCity(city.getID());
        dbAdapter.close();
    }
    public void updateCity(City city)
    {
        cities.set(getCityIndex(city), city);
        dbAdapter.open();
        dbAdapter.updateCity(city.getID(), city);
        dbAdapter.close();
    }
    public void undoCity(int position, City city)
    {
        cities.add(position, city);
        dbAdapter.open();
        dbAdapter.insertCity(city);
        dbAdapter.close();
    }
    public long getCityID(String cowmid)
    {
        for(int i=0; i<cities.size(); ++i)
        {
            if(cities.get(i).getCityOwmID().equals(cowmid))
                return cities.get(i).getID();
        }
        return -1;
    }
    public int getCityIndex(City city)
    {
        for(int i=0; i<cities.size(); ++i)
        {
            if(cities.get(i).getCityOwmID().equals(city.getCityOwmID()))
                return i;
        }
        return -1;
    }
}
