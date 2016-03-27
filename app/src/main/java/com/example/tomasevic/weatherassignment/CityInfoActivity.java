package com.example.tomasevic.weatherassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CityInfoActivity extends AppCompatActivity
{
    TextView cityNameInfo;
    TextView cityTempInfo;
    TextView cityHumidityInfo;
    TextView cityDescriptionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);

        Bundle extraIntent = getIntent().getExtras();
        int index = extraIntent.getInt("index");

        cityNameInfo = (TextView)findViewById(R.id.cityNameInfo);
        cityTempInfo = (TextView)findViewById(R.id.cityTempInfo);
        cityHumidityInfo = (TextView)findViewById(R.id.cityHumidityInfo);
        cityDescriptionInfo = (TextView)findViewById(R.id.cityDescriptionInfo);

        City city = CitiesData.getInstance().getCity(index);

        cityNameInfo.setText(city.getName());
        cityTempInfo.setText(city.getTemp());
        cityHumidityInfo.setText(city.getHumidity());
        cityDescriptionInfo.setText(city.getDescription());
    }
}
