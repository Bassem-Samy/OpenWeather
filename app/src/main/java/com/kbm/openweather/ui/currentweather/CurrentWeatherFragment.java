package com.kbm.openweather.ui.currentweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kbm.openweather.R;
import com.kbm.openweather.models.CurrentWeatherDisplay;
import com.kbm.openweather.ui.currentweather.di.CurrentWeatherModule;
import com.kbm.openweather.ui.currentweather.di.DaggerCurrentWeatherComponent;
import com.kbm.openweather.utils.Constants;
import com.kbm.openweather.utils.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
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
    @BindView(R.id.txt_current_degree)
    TextView currentDegreeTextView;
    @BindView(R.id.txt_country)
    TextView countryTextView;
    @BindView(R.id.txt_humidity)
    TextView humidityTextView;
    @BindView(R.id.txt_main_description)
    TextView mainDescriptionTextView;
    @BindView(R.id.txt_sunrise)
    TextView sunriseTextView;
    @BindView(R.id.txt_sunset)
    TextView sunsetTextView;
    @BindView(R.id.txt_wind)
    TextView windTextView;
    @BindView(R.id.img_icon)
    ImageView iconImageView;
    @BindView(R.id.lnr_current_weather)
    LinearLayout currentWeatherLinearLayout;
    @BindView(R.id.prgrs_current_weather)
    ProgressBar mainProgressBar;

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
        mPresenter.getCurrentWeatherByLocation(Constants.OPEN_WEATHER_KEY, mLatitude, mLongitude, Constants.CURRENT_WEATHER_UNIT.getValue());
    }

    @Override
    public void showProgress() {
        mainProgressBar.setVisibility(View.VISIBLE);
        currentWeatherLinearLayout.setVisibility(View.INVISIBLE);

    }

    @Override
    public void hideProgress() {
        mainProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void showError() {

        makeToast(R.string.general_error);
    }

    @Override
    public void showNoInternet() {
        makeToast(R.string.no_internet_message);
    }

    @Override
    public void showNoData() {
        makeToast(R.string.there_is_no_data_to_display);
    }

    @Override
    public void updateData(CurrentWeatherDisplay currentWeather) {

        currentWeatherLinearLayout.setVisibility(View.VISIBLE);
        currentDegreeTextView.setText(currentWeather.getTemperature());
        countryTextView.setText(currentWeather.getCountry());
        humidityTextView.setText(currentWeather.getHumidity());
        sunriseTextView.setText(currentWeather.getSunrise());
        sunsetTextView.setText(currentWeather.getSunset());
        windTextView.setText(currentWeather.getWind());
        mainDescriptionTextView.setText(currentWeather.getMainDescription());
        ImageLoader.loadImage(currentWeather.getIcon(), iconImageView);
    }


    private void makeToast(@StringRes int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
