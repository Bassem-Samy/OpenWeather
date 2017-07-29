package com.kbm.openweather.ui.forecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kbm.openweather.R;
import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.ui.forecast.di.DaggerForecastComponent;
import com.kbm.openweather.ui.forecast.di.ForecastModule;
import com.kbm.openweather.utils.Constants;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A simple Fragment that displays the forecast for the next 5 days of the current location
 */
public class ForecastFragment extends Fragment implements ForecastView {
    public static final String TAG = "forecast_fragment";
    private static final String ARG_LATITUDE = "arg_latitude";
    private static final String ARG_LONGITUDE = "arg_longitude";

    private String mLatitude;
    private String mLongitude;
    @Inject
    ForecastPresenter mPresenter;

    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param latitude  .
     * @param longitude .
     * @return A new instance of fragment ForecastFragment.
     */
    public static ForecastFragment newInstance(String latitude, String longitude) {
        ForecastFragment fragment = new ForecastFragment();
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
        DaggerForecastComponent.builder().forecastModule(new ForecastModule(this, Constants.BASE_URL, getContext())).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getForecast(Constants.OPEN_WEATHER_KEY, mLatitude, mLongitude, Constants.CURRENT_WEATHER_UNIT.getValue());

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
    public void updateData(ForecastResponse forecastResponse) {

    }
}
