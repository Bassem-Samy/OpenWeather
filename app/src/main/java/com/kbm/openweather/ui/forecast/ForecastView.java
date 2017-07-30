package com.kbm.openweather.ui.forecast;

import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.utils.BaseView;

import java.util.List;

/**
 * Created by Bassem on 7/29/2017.
 */

public interface ForecastView extends BaseView {
    void updateData(List<ForecastDay> forecastDays);
}
