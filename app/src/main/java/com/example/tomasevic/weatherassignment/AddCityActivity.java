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

    public void searchForCity()
    {
        WeatherAsyncTask task = new WeatherAsyncTask();

        try
        {
            searchResult = (ArrayList<City>)task.execute(new String[]{citySearchName.getText().toString()}).get();
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
                if(citySearchName.getText().length() != 0 && !citySearchName.getText().toString().contains(" "))
                    searchForCity();
                else
                    Toast.makeText(AddCityActivity.this, getText(R.string.enter_city_name), Toast.LENGTH_SHORT).show();
                break;
            case R.id.addCityButton:
                if(searchResult != null)
                {
                    CitiesData.getInstance().addNewCity(searchResult.get(0));
                    Toast.makeText(AddCityActivity.this, getText(R.string.city_added), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
