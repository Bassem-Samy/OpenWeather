package com.kbm.openweather.ui.forecast;

/**
 * Created by Bassem on 7/29/2017.
 */

public interface ForecastPresenter {
    void getForecast(String appId, String latitude, String longitude, String unit);

    void onDestroy();
}
