package com.kbm.openweather.ui.currentweather;

/**
 * Created by Bassem on 7/28/2017.
 */

public interface CurrentWeatherPresenter {
    void getCurrentWeatherByLocation(String appId, String latitude, String longitude,String unit);

    void onDestroy();
}
