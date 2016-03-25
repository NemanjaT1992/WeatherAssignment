package com.example.tomasevic.weatherassignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CitiesListItemHolder>
{
    private final List<CityListItem> cityListItems;

    public CitiesListAdapter(List<CityListItem> cli)
    {
        cityListItems = cli;
    }

    @Override
    public CitiesListItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item_layout, parent, false);

        CitiesListItemHolder citiesListItemHolder = new CitiesListItemHolder(view);
        return citiesListItemHolder;
    }

    @Override
    public void onBindViewHolder(CitiesListItemHolder holder, int position)
    {
        holder.cityName.setText(cityListItems.get(position).getCityName());
        holder.cityTemp.setText(cityListItems.get(position).getCityTemp());
    }

    @Override
    public int getItemCount()
    {
        return cityListItems.size();
    }

    public void addItem(CityListItem cli, int index)
    {
        cityListItems.add(cli);
        notifyItemInserted(index);
    }

    public void deleteItem(int index)
    {
        cityListItems.remove(index);
        notifyItemRemoved(index);
    }

    public static class CitiesListItemHolder extends RecyclerView.ViewHolder
    {
        TextView cityName;
        TextView cityTemp;

        public CitiesListItemHolder(View itemView)
        {
            super(itemView);

            cityName = (TextView)itemView.findViewById(R.id.cityName);
            cityTemp = (TextView)itemView.findViewById(R.id.cityTemp);
        }
    }

}
