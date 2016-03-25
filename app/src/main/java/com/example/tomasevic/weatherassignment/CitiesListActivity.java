package com.example.tomasevic.weatherassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class CitiesListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter citiesListAdapter;
    private RecyclerView.LayoutManager rvlayoutManager;
    private ArrayList<CityListItem> cityItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.citiesRecycleView);
        rvlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvlayoutManager);

        cityItemList = new ArrayList<>();

        //-----------------------------------------tmp data
//        CityListItem cli = new CityListItem();
//        cli.setCityName("Nis");
//        cli.setCityTemp("10");
//        cityItemList.add(cli);
//
//        CityListItem cli1 = new CityListItem();
//        cli1.setCityName("Beograd");
//        cli1.setCityTemp("19");
//        cityItemList.add(cli1);
        //-----------------------------------------

        citiesListAdapter = new CitiesListAdapter(cityItemList);
        recyclerView.setAdapter(citiesListAdapter);

//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addCityIntent = new Intent(CitiesListActivity.this, AddCityActivity.class);
                startActivity(addCityIntent);
            }
        });

        if(cityItemList.isEmpty())
        {
            Snackbar.make(recyclerView, getResources().getText(R.string.empty_cities_list), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
