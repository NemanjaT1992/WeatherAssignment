package com.example.tomasevic.weatherassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tomasevic on 26.3.2016..
 */
public class WeatherAssignmentDBAdapter
{
    public static final String DB_NAME = "WeatherAssignmentDB";
    public static final String DB_TABLE = "CitiesInfo";
    public static final int DB_VERSION = 1;

    public static final String CITY_ID = "ID";
    public static final String CITY_NAME = "Name";
    public static final String CITY_TEMP = "Temp";
    public static final String CITY_HUMIDITY = "Humidity";
    public static final String CITY_DESCRIPTION = "Description";
    public static final String CITY_COUNTRY = "Country";
    public static final String CITY_OWM_ID = "city_owm_id";

    private SQLiteDatabase db;
    private final Context context;
    private WeatherAssignmentDBHelper dbHelper;

    public WeatherAssignmentDBAdapter(Context cont)
    {
        context = cont;
        dbHelper = new WeatherAssignmentDBHelper(context, DB_NAME, null, DB_VERSION);
    }

    public WeatherAssignmentDBAdapter open() throws SQLiteException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }
    public long insertCity(City entry)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(CITY_NAME, entry.getName());
        contentValues.put(CITY_TEMP, entry.getTemp());
        contentValues.put(CITY_HUMIDITY, entry.getHumidity());
        contentValues.put(CITY_DESCRIPTION, entry.getDescription());
        contentValues.put(CITY_COUNTRY, entry.getCountry());
        contentValues.put(CITY_OWM_ID, entry.getCityOwmID());

        long id = -1;

        db.beginTransaction();
        try
        {
            id = db.insert(DB_TABLE, null, contentValues);
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            Log.w("WADBAdapter", e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        return id;
    }
    public boolean removeCity(long id)
    {
        boolean success = false;
        db.beginTransaction();
        try
        {
            success = db.delete(DB_TABLE, CITY_ID + "=" + id, null) > 0;
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            Log.w("WADBAdapter", e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        return success;
    }
    public ArrayList<City> getAllCities()
    {
        ArrayList<City> citiesArrayList = new ArrayList<>();

        Cursor cursor = null;
        db.beginTransaction();

        try
        {
            cursor = db.query(DB_TABLE, null, null, null, null, null, null);
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            Log.w("WADBAdapter", e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        if (cursor != null)
        {
            citiesArrayList = new ArrayList<>();
            City city = null;
            while (cursor.moveToNext())
            {
                city = new City();
                city.setID(cursor.getLong(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_NAME)));
                city.setTemp(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_TEMP)));
                city.setHumidity(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_HUMIDITY)));
                city.setDescription(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_DESCRIPTION)));
                city.setCountry(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_COUNTRY)));
                city.setCityOwmID(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_OWM_ID)));
                citiesArrayList.add(city);
            }
        }
        return citiesArrayList;
    }
    public City getCity(long id)
    {
        City city = null;
        Cursor cursor = null;
        db.beginTransaction();

        try
        {
            cursor = db.query(DB_TABLE, null, CITY_ID + "=" + id, null, null, null, null);
            db.setTransactionSuccessful();
        }
        catch (SQLiteException e)
        {
            Log.w("WADBAdapter", e.getMessage());
        }
        finally
        {
            db.endTransaction();
        }
        if (cursor != null)
        {
            while (cursor.moveToFirst())
            {
                city = new City();
                city.setID(cursor.getLong(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_ID)));
                city.setName(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_NAME)));
                city.setTemp(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_TEMP)));
                city.setHumidity(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_HUMIDITY)));
                city.setDescription(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_DESCRIPTION)));
                city.setCountry(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_COUNTRY)));
                city.setCityOwmID(cursor.getString(cursor.getColumnIndex(WeatherAssignmentDBAdapter.CITY_OWM_ID)));
            }
        }
        return city;
    }
    public boolean updateCity(long id, City entry)
    {
        String where = CITY_ID + "=" + id;

        ContentValues contentValues = new ContentValues();

        contentValues.put(CITY_NAME, entry.getName());
        contentValues.put(CITY_TEMP, entry.getTemp());
        contentValues.put(CITY_HUMIDITY, entry.getHumidity());
        contentValues.put(CITY_DESCRIPTION, entry.getDescription());
        contentValues.put(CITY_COUNTRY, entry.getCountry());
        contentValues.put(CITY_OWM_ID, entry.getCityOwmID());

        return db.update(DB_TABLE, contentValues, where, null) > 0;
    }
}
























