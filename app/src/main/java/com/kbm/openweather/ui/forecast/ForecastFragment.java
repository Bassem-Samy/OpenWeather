package com.kbm.openweather.ui.forecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kbm.openweather.R;
import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.ui.forecast.adapters.ForecastFragmentPagerAdapter;
import com.kbm.openweather.ui.forecast.di.DaggerForecastComponent;
import com.kbm.openweather.ui.forecast.di.ForecastModule;
import com.kbm.openweather.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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
    @BindView(R.id.prgrs_main)
    ProgressBar mainProgressBar;
    @BindView(R.id.vp_forecast)
    ViewPager forecastViewPager;
    @BindView(R.id.tab_layout_days)
    TabLayout daysTabLayout;
    ForecastFragmentPagerAdapter mPagerAdapter;

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
        mainProgressBar.setVisibility(View.VISIBLE);
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
    public void updateData(List<ForecastDay> items) {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ForecastFragmentPagerAdapter(getChildFragmentManager(), items);
            forecastViewPager.setAdapter(mPagerAdapter);
            daysTabLayout.setupWithViewPager(forecastViewPager);
        }

    }

    private void makeToast(@StringRes int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
