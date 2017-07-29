package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastItem {
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public WeatherSys getWatherSys() {
        return watherSys;
    }

    public void setWatherSys(WeatherSys watherSys) {
        this.watherSys = watherSys;
    }

    public List<WeatherItem> getWeatherItems() {
        return weatherItems;
    }

    public void setWeatherItems(List<WeatherItem> weatherItems) {
        this.weatherItems = weatherItems;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public MainWeatherInfo getMainWeatherInfo() {
        return mainWeatherInfo;
    }

    public void setMainWeatherInfo(MainWeatherInfo mainWeatherInfo) {
        this.mainWeatherInfo = mainWeatherInfo;
    }

    @SerializedName("dt")
    private String dateTime;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("sys")
    private WeatherSys watherSys;
    @SerializedName("weather")
    private List<WeatherItem> weatherItems;
    @SerializedName("dt_txt")
    private String dateText;
    @SerializedName("main")
    private MainWeatherInfo mainWeatherInfo;

}
