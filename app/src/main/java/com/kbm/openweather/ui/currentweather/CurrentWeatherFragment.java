package com.kbm.openweather.ui.currentweather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kbm.openweather.R;
import com.kbm.openweather.models.CurrentWeatherResponse;
import com.kbm.openweather.ui.currentweather.di.CurrentWeatherModule;
import com.kbm.openweather.ui.currentweather.di.DaggerCurrentWeatherComponent;
import com.kbm.openweather.utils.Constants;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple That displays current weather by location
 */
public class CurrentWeatherFragment extends Fragment implements CurrentWeatherView {
    public static String TAG = "current_weather_fragment";
    private static final String ARG_LATITUDE = "arg_latitude";
    private static final String ARG_LONGITUDE = "arg_longitude";
    private String mLatitude;
    private String mLongitude;
    @Inject
    CurrentWeatherPresenter mPresenter;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param latitude  for location.
     * @param longitude for location.
     * @return A new instance of fragment CurrentWeatherFragment.
     */
    public static CurrentWeatherFragment newInstance(String latitude, String longitude) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LATITUDE, latitude);
        args.putString(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLatitude = getArguments().getString(ARG_LATITUDE);
            mLongitude = getArguments().getString(ARG_LONGITUDE);
        }
        DaggerCurrentWeatherComponent.builder().currentWeatherModule(new CurrentWeatherModule(this, Constants.BASE_URL, getContext())).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getCurrentWeatherByLocation(Constants.OPEN_WEATHER_KEY, mLatitude, mLongitude);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoInternet() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void updateData(CurrentWeatherResponse currentWeather) {

    }
}
