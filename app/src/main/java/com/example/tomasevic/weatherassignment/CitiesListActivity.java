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
                swipeRefreshLayout.setEnabled(false);
                refreshCities();
                swipeRefreshLayout.setEnabled(true);
            }
        });

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

        if(CitiesData.getInstance().getCities().size() == 0)
        {
            Snackbar.make(recyclerView, getResources().getText(R.string.empty_cities_list), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir)
            {
                final City deletedCity = CitiesData.getInstance().getCity(viewHolder.getPosition());
                final int position = viewHolder.getPosition();

                CitiesData.getInstance().deleteCity(viewHolder.getPosition());
                citiesListAdapter.notifyDataSetChanged();

                SnackbarManager.show(com.nispok.snackbar.Snackbar.with(CitiesListActivity.this)
                        .text(getText(R.string.undo_delete))
                        .actionLabel(getText(R.string.undo))
                        .actionListener(new ActionClickListener()
                        {
                            @Override
                            public void onActionClicked(com.nispok.snackbar.Snackbar snackbar)
                            {
//                                Toast.makeText(CitiesListActivity.this, getText(R.string.undo_delete), Toast.LENGTH_SHORT).show();
                                CitiesData.getInstance().undoCity(position, deletedCity);
                                citiesListAdapter.notifyDataSetChanged();
                            }
                        }));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
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
    public void refreshCities()
    {
        String idsList = "";
        ArrayList<City> listToUpdate, updatedList;
        listToUpdate = CitiesData.getInstance().getCities();
        updatedList = new ArrayList<>();
        WeatherAsyncTask task = new WeatherAsyncTask();

        for(int j=0; j<listToUpdate.size()-1; ++j)
            idsList += listToUpdate.get(j).getCityOwmID() + "-";
        idsList += listToUpdate.get(listToUpdate.size()-1).getCityOwmID();

        try
        {
            updatedList = (ArrayList<City>) task.execute(new String[]{idsList}).get();
//                tmpArray.add((City)tasksArray.get(i).execute(new String[]{refreshList.get(i).getName()}).get());
//                CitiesData.getInstance().updateCity(tmpArray.get(i));
//                CitiesData.getInstance().updateCity(currentRefreshCity);

            for(int j=0; j<updatedList.size(); ++j)
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
}

















