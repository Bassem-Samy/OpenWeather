package com.kbm.openweather.ui.forecast.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.ui.forecast.ForecastDayFragment;

import java.util.List;

/**
 * Created by Bassem on 7/30/2017.
 */

public class ForecastFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<ForecastDay> mDataset;

    public ForecastFragmentPagerAdapter(FragmentManager fm, List<ForecastDay> items) {
        super(fm);
        this.mDataset = items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataset.get(position).getDateText();
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastDayFragment.newInstance(mDataset.get(position));
    }

    @Override
    public int getCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }
}
