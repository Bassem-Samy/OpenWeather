package com.kbm.openweather.ui.forecast.di;

import com.kbm.openweather.ui.forecast.ForecastFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bassem on 7/29/2017.
 */
@Singleton
@Component(modules = {ForecastModule.class})
public interface ForecastComponent {
    void inject(ForecastFragment target);
}
