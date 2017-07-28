package com.kbm.openweather.ui.currentweather;

import com.kbm.openweather.models.CurrentWeatherResponse;
import com.kbm.openweather.network.WeatherService;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Bassem on 7/28/2017.
 */

public class CurrentWeatherInteractorImpl implements CurrentWeatherInteractor {
    private Retrofit mRetrofit;

    public CurrentWeatherInteractorImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Single<CurrentWeatherResponse> getCurrentWeatherByLocation(String appid, String latitude, String longitude) {
        return mRetrofit.create(WeatherService.class).getCurrentWeatherByLocation(appid, latitude, longitude);
    }
}
