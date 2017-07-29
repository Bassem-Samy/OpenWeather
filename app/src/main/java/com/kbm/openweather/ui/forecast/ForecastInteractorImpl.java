package com.kbm.openweather.ui.forecast;

import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.network.WeatherService;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastInteractorImpl implements ForecastInteractor {
    private Retrofit mRetrofit;

    public ForecastInteractorImpl(Retrofit retrofit) {
        this.mRetrofit = retrofit;
    }

    @Override
    public Single<ForecastResponse> getForecastByLocation(String appId, String latitude, String longitude, String unit) {
        return mRetrofit.create(WeatherService.class).getForecastByLocation(appId, latitude, longitude, unit);
    }
}
