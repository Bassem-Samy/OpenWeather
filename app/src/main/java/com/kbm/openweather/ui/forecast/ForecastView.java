package com.kbm.openweather.ui.forecast;

import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.utils.BaseView;

/**
 * Created by Bassem on 7/29/2017.
 */

public interface ForecastView extends BaseView {
    void updateData(ForecastResponse forecastResponse);
}
