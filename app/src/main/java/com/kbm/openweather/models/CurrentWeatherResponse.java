package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem on 7/28/2017.
 */

public class CurrentWeatherResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("dt")
    private String dateTime;
    @SerializedName("clouds")
    private Cloud clouds;
    @SerializedName("coord")
    private Coord coord;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("cod")
    private String cod;
    @SerializedName("sys")
    private WeatherSys watherSys;
    @SerializedName("name")
    private String name;
    @SerializedName("base")
    private String base;
    @SerializedName("weather")
    private List<WeatherItem> weather;
    @SerializedName("main")
    private MainWeatherInfo mainWeatherInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public WeatherSys getWatherSys() {
        return watherSys;
    }

    public void setWatherSys(WeatherSys watherSys) {
        this.watherSys = watherSys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public MainWeatherInfo getMainWeatherInfo() {
        return mainWeatherInfo;
    }

    public void setMainWeatherInfo(MainWeatherInfo mainWeatherInfo) {
        this.mainWeatherInfo = mainWeatherInfo;
    }

}
