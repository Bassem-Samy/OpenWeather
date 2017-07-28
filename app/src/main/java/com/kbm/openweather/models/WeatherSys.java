package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class WeatherSys {
    @SerializedName("message")
    private String message;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("country")
    private String country;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
