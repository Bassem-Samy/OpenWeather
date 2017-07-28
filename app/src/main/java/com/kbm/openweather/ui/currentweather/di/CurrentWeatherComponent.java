package com.kbm.openweather.ui.currentweather.di;

import com.kbm.openweather.ui.currentweather.CurrentWeatherFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bassem on 7/28/2017.
 */
@Singleton
@Component(modules = {CurrentWeatherModule.class})
public interface CurrentWeatherComponent {
    void inject(CurrentWeatherFragment target);
}
