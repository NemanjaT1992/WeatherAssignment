package com.example.tomasevic.weatherassignment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CitiesListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter citiesListAdapter;
    private RecyclerView.LayoutManager rvlayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyListTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.citiesRecycleView);
        rvlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvlayoutManager);

        citiesListAdapter = new CitiesListAdapter(CitiesData.getInstance().getCities());
        recyclerView.setAdapter(citiesListAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(checkConnectivity() != null)
                {
                    swipeRefreshLayout.setEnabled(false);
                    refreshCities();
                    swipeRefreshLayout.setEnabled(true);
                }
                else
                {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(CitiesListActivity.this, getText(R.string.internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        emptyListTV = (TextView) findViewById(R.id.emptyList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addCityIntent = new Intent(CitiesListActivity.this, AddCityActivity.class);
                startActivity(addCityIntent);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                final boolean[] undo = {false};
                final int position = viewHolder.getPosition();
                final City deletedCity = CitiesData.getInstance().getCity(position);

                CitiesData.getInstance().removeCityFromList(position);
                citiesListAdapter.notifyDataSetChanged();


                Snackbar snackbar = Snackbar.make(recyclerView, getText(R.string.undo_delete).toString(), Snackbar.LENGTH_LONG);
                snackbar.setAction(getText(R.string.undo), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        CitiesData.getInstance().undoCity(position, deletedCity);
                        citiesListAdapter.notifyDataSetChanged();
                        undo[0] = true;
                    }
                });
                snackbar.setCallback(new Snackbar.Callback()
                {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event)
                    {
                        super.onDismissed(snackbar, event);
                        if(!undo[0])
                            CitiesData.getInstance().deleteCity(deletedCity);

                        if(CitiesData.getInstance().getCities().size() == 0)
                            emptyListTV.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onShown(Snackbar snackbar)
                    {
                        super.onShown(snackbar);
                    }
                });
                snackbar.setActionTextColor(getResources().getColor(R.color.red));
                snackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if(CitiesData.getInstance().getCities().size() == 0)
            emptyListTV.setVisibility(View.VISIBLE);
        else
            emptyListTV.setVisibility(View.GONE);

        citiesListAdapter.notifyDataSetChanged();

        ((CitiesListAdapter)citiesListAdapter).setOnItemClickListener(new CitiesListAdapter.ListOnClickListener()
        {
            @Override
            public void onItemClickListener(int position, View v, long id)
            {
                Intent cityInfoIntent = new Intent(CitiesListActivity.this, CityInfoActivity.class);
                cityInfoIntent.putExtra("index", position);
                startActivity(cityInfoIntent);
            }
        });
    }

    public void refreshCities()
    {
        String idsList = "";
        ArrayList<City> listToUpdate, updatedList;
        listToUpdate = CitiesData.getInstance().getCities();
        updatedList = new ArrayList<>();
        WeatherAsyncTask task = new WeatherAsyncTask();

        if (listToUpdate.size() == 1)
            idsList = listToUpdate.get(0).getCityOwmID().toString() + "-";

        for (int j = 0; j < listToUpdate.size() - 1; ++j)
            idsList += listToUpdate.get(j).getCityOwmID() + "-";
        idsList += listToUpdate.get(listToUpdate.size() - 1).getCityOwmID();

        try
        {
            updatedList = (ArrayList<City>) task.execute(new String[]{idsList}).get();

            for (int j = 0; j < updatedList.size(); ++j)
                CitiesData.getInstance().updateCity(updatedList.get(j));

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        citiesListAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
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

















