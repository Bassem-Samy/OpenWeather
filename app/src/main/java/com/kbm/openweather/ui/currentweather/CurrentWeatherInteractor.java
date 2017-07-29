package com.kbm.openweather.ui.currentweather;

import com.kbm.openweather.models.CurrentWeatherResponse;

import io.reactivex.Single;

/**
 * Created by Bassem on 7/28/2017.
 */

public interface CurrentWeatherInteractor {

    Single<CurrentWeatherResponse> getCurrentWeatherByLocation(String appid, String latitude, String longitude,String unit);
}
