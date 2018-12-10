package com.marnysofton.weatherapp.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marnysofton.weatherapp.Model.City;
import com.marnysofton.weatherapp.R;


public class CityDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "CityToShow";

    private City CityToShow;


    public CityDetailFragment() {
        // Required empty public constructor
    }

    public static CityDetailFragment newInstance(City CityToShow) {
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, CityToShow);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CityToShow = (City) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_city_detail, container, false);

        ((TextView)view.findViewById(R.id.text_CityName)).setText(CityToShow.getName());
        ((TextView)view.findViewById(R.id.text_WeatherDescription)).setText(CityToShow.getWeather().get(0).getDescription());
        ((TextView)view.findViewById(R.id.text_CityTemperature)).setText(CityToShow.getMain().getTemp() + view.getContext().getString(R.string.Celsius));
        ((TextView)view.findViewById(R.id.text_CityHumidity)).setText(CityToShow.getMain().getHumidity() + view.getContext().getString(R.string.percentage));
        ((TextView)view.findViewById(R.id.text_CityWindSpeed)).setText(CityToShow.getWind().getSpeed() + view.getContext().getString(R.string.metersec));

        return view;

    }

}
