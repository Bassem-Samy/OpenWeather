package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastResponse {
    public List<ForecastItem> getForecastItems() {
        return forecastItems;
    }

    public void setForecastItems(List<ForecastItem> forecastItems) {
        this.forecastItems = forecastItems;
    }

    @SerializedName("list")
    List<ForecastItem> forecastItems;
}
