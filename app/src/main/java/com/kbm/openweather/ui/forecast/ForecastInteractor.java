package com.kbm.openweather.ui.forecast;

import com.kbm.openweather.models.ForecastResponse;

import io.reactivex.Single;

/**
 * Created by Bassem on 7/29/2017.
 */

public interface ForecastInteractor {
    Single<ForecastResponse> getForecastByLocation(String appId,String latitude,String longitude,String unit);
}
