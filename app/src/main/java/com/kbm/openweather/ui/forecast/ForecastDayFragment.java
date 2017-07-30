package com.kbm.openweather.ui.forecast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kbm.openweather.R;
import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.ui.forecast.adapters.ForecastItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple Fragment that displays current forecast for a day
 */
public class ForecastDayFragment extends Fragment {
    private static final String ARG_FORECAST_DAY = "arg_forecast_day";
    private ForecastDay mForecastDay;

    @BindView(R.id.rclr_forecast_day)
    RecyclerView dayRecyclerView;
    ForecastItemAdapter mAdapter;

    public ForecastDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param forecastDay .
     * @return A new instance of fragment ForecastDayFragment.
     */
    public static ForecastDayFragment newInstance(ForecastDay forecastDay) {
        ForecastDayFragment fragment = new ForecastDayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FORECAST_DAY, forecastDay);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForecastDay = getArguments().getParcelable(ARG_FORECAST_DAY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast_day, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateRecyclerView();
    }

    private void updateRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new ForecastItemAdapter(mForecastDay.getForecastItems());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            dayRecyclerView.setLayoutManager(linearLayoutManager);
            dayRecyclerView.setAdapter(mAdapter);
        }
    }
}
