package com.kbm.openweather.network;

import com.kbm.openweather.models.CurrentWeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bassem on 7/28/2017.
 */

public interface WeatherService {
    @GET("weather")
    Single<CurrentWeatherResponse> getCurrentWeatherByLocation(@Query("appid") String appId, @Query("lat") String latitude, @Query("lon") String longitude);
}
