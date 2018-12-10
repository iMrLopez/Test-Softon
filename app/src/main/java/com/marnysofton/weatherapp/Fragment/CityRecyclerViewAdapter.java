package com.marnysofton.weatherapp.Fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marnysofton.weatherapp.Fragment.CitiesListFragment.OnListFragmentInteractionListener;
import com.marnysofton.weatherapp.Model.City;
import com.marnysofton.weatherapp.R;

import java.util.List;


public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {

    private final List<City> mCitiesLists;
    private final OnListFragmentInteractionListener mListener;

    public CityRecyclerViewAdapter(List<City> items, OnListFragmentInteractionListener listener) {
        mCitiesLists = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mCitiesLists.get(position);
        holder.mCityName.setText(mCitiesLists.get(position).getName());
        holder.mCityTemp.setText(mCitiesLists.get(position).getMain().getTemp() + holder.itemView.getContext().getString(R.string.Celsius));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCitiesLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mCityName;
        final TextView mCityTemp;
        City mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mCityName = (TextView) view.findViewById(R.id.cityName);
            mCityTemp = (TextView) view.findViewById(R.id.cityTemperature);
        }
    }
}
