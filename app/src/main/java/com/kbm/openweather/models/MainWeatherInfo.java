package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class MainWeatherInfo {
    @SerializedName("humidity")
    private String humidity;
    @SerializedName("pressure")
    private String pressure;
    @SerializedName("temp_max")
    private String tempMax;
    @SerializedName("sea_level")
    private String seaLevel;
    @SerializedName("temp_min")
    private String tempMin;
    @SerializedName("temp")
    private String temp;
    @SerializedName("grnd_level")
    private String groundLevel;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(String seaLevel) {
        this.seaLevel = seaLevel;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(String groundLevel) {
        this.groundLevel = groundLevel;
    }
}
