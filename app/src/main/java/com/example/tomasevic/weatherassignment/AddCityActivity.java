package com.example.tomasevic.weatherassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddCityActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText citySearchName;
    Button addCityButton, search;
    ListView cityResultView;
    ArrayList<String> result;
    ArrayAdapter<String> resultAdapter;
    ArrayList<City> searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        citySearchName = (EditText) findViewById(R.id.citySearchName);

        addCityButton = (Button)findViewById(R.id.addCityButton);
        addCityButton.setOnClickListener(this);

        search = (Button)findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        cityResultView = (ListView)findViewById(R.id.cityResult);
        result = new ArrayList<>();

        resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, result);

        cityResultView.setAdapter(resultAdapter);
        searchResult = new ArrayList<>();
    }

    public void searchForCity(String name)
    {
        WeatherAsyncTask task = new WeatherAsyncTask();

        try
        {
            searchResult = (ArrayList<City>)task.execute(new String[]{name}).get();
            result.clear();
            if(searchResult != null)
            {
                result.add(searchResult.get(0).getName() + ", " + searchResult.get(0).getCountry());
                resultAdapter.notifyDataSetChanged();
            }

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.searchButton:
                if (citySearchName.getText().length() != 0)
                {
                    if(checkConnectivity() != null)
                    {
                        String cityName = citySearchName.getText().toString();
                        if (cityName.contains(" "))
                        {
                            cityName = "";
                            String[] multiWordCity = citySearchName.getText().toString().split(" ");
                            for (int i = 0; i < multiWordCity.length; ++i)
                                cityName += multiWordCity[i];
                        }
                        searchForCity(cityName);
                    }
                    else
                        Toast.makeText(AddCityActivity.this, getText(R.string.internet), Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(AddCityActivity.this, getText(R.string.enter_city_name), Toast.LENGTH_SHORT).show();
                break;
            case R.id.addCityButton:
                if(searchResult.size() != 0)
                {
                    CitiesData.getInstance().addNewCity(searchResult.get(0));
                    Toast.makeText(AddCityActivity.this, getText(R.string.city_added), Toast.LENGTH_SHORT).show();
                    result.clear();
                    resultAdapter.notifyDataSetChanged();
                    citySearchName.setText("");
                }
                else
                    Toast.makeText(AddCityActivity.this, getText(R.string.empty_search), Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public Object checkConnectivity()
    {
        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        Object available = null;
        try
        {
            available = connectionDetector.execute(new String[]{"available"}).get();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return available;
    }
}
