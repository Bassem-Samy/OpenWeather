package com.kbm.openweather.utils;

/**
 * Created by Bassem on 7/29/2017.
 */

public enum WeatherUnits {
    IMPERIAL("imperial"), METRIC("metric");
    private String value;

    WeatherUnits(String val) {
        this.value = val;
    }

    public String getValue() {
        return this.value;
    }
}
