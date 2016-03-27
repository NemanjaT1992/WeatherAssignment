package com.example.tomasevic.weatherassignment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tomasevic on 25.3.2016..
 */
public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.CitiesListItemHolder>
{
    private final List<City> citiesList;
    private static ListOnClickListener clickListener;

    public CitiesListAdapter(List<City> cli)
    {
        citiesList = cli;
    }

    @Override
    public CitiesListItemHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_item_layout, parent, false);

        CitiesListItemHolder citiesListItemHolder = new CitiesListItemHolder(view);

        return citiesListItemHolder;
    }

    @Override
    public void onBindViewHolder(CitiesListItemHolder holder, int position)
    {
        holder.cityName.setText(citiesList.get(position).getName());
        holder.cityTemp.setText(citiesList.get(position).getTemp());
        holder.id = citiesList.get(position).getID();

        String teperatureString = citiesList.get(position).getTemp();
        int temperature = Integer.parseInt(teperatureString.substring(0, teperatureString.length()-2));
        if(temperature < 15)
            holder.cityTemp.setBackgroundColor(Color.parseColor("#0094ff"));
        else if(temperature >=15 && temperature < 24)
            holder.cityTemp.setBackgroundColor(Color.parseColor("#00ff5c"));
        else
            holder.cityTemp.setBackgroundColor(Color.parseColor("#FFA500"));
    }

    @Override
    public int getItemCount()
    {
        return citiesList.size();
    }

    public void addItem(City cli, int index)
    {
        citiesList.add(cli);
        notifyItemInserted(index);
    }

    public void deleteItem(int index)
    {
        citiesList.remove(index);
        notifyItemRemoved(index);
    }

    public void setOnItemClickListener(ListOnClickListener myClickListener) {
        this.clickListener = myClickListener;
    }

    public static class CitiesListItemHolder extends RecyclerView.ViewHolder
    {
        TextView cityName;
        TextView cityTemp;
        long id;

        public CitiesListItemHolder(View itemView)
        {
            super(itemView);

            cityName = (TextView)itemView.findViewById(R.id.cityName);
            cityTemp = (TextView)itemView.findViewById(R.id.cityTemp);

            cityName.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clickListener.onItemClickListener(getPosition(), v, id);
                }
            });
        }
    }
    public interface ListOnClickListener
    {
        public void onItemClickListener(int position, View v, long name);
    }
}
