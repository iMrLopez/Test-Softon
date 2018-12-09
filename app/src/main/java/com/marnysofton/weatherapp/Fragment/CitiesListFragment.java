package com.marnysofton.weatherapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marnysofton.weatherapp.Model.City;
import com.marnysofton.weatherapp.R;

import java.util.ArrayList;


public class CitiesListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String CITIES_TO_DISPLAY = "cities-to-display";

    private int mColumnCount = 1;
    private ArrayList<City> cities;
    private OnListFragmentInteractionListener mListener;


    public CitiesListFragment() {
    }


    public static CitiesListFragment newInstance(int ColumnCount, ArrayList<City> citiesToDisplay) {
        CitiesListFragment fragment = new CitiesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, ColumnCount);
        args.putSerializable(CITIES_TO_DISPLAY, citiesToDisplay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            cities = (ArrayList<City>)getArguments().getSerializable(CITIES_TO_DISPLAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new CityRecyclerViewAdapter(cities, mListener));
        }
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CitiesListFragment.OnListFragmentInteractionListener) {
            mListener = (CitiesListFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(City item);
    }


}