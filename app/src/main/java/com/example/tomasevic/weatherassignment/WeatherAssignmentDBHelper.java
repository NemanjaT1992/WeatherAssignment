package com.example.tomasevic.weatherassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Tomasevic on 26.3.2016..
 */
public class WeatherAssignmentDBHelper extends SQLiteOpenHelper
{
    public static final String DB_CREATE = "create table " + WeatherAssignmentDBAdapter.DB_TABLE + " ("
            + WeatherAssignmentDBAdapter.CITY_ID + " integer primary key autoincrement, "
            + WeatherAssignmentDBAdapter.CITY_NAME + " text, "
            + WeatherAssignmentDBAdapter.CITY_TEMP + " text, "
            + WeatherAssignmentDBAdapter.CITY_HUMIDITY + " text, "
            + WeatherAssignmentDBAdapter.CITY_DESCRIPTION + " text, "
            + WeatherAssignmentDBAdapter.CITY_OWM_ID + " long, "
            + WeatherAssignmentDBAdapter.CITY_COUNTRY + " text);";

    public WeatherAssignmentDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(DB_CREATE);
        }
        catch (SQLiteException e)
        {
            Log.w("WADBHelper", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS" + WeatherAssignmentDBAdapter.DB_TABLE);
    }
}
