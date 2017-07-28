package com.kbm.openweather.ui.currentweather;

import com.kbm.openweather.models.CurrentWeatherResponse;
import com.kbm.openweather.utils.BaseView;

/**
 * Created by Bassem on 7/28/2017.
 */

public interface CurrentWeatherView extends BaseView {

    void updateData(CurrentWeatherResponse currentWeather);

}
