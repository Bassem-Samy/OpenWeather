package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class Wind {
    @SerializedName("speed")
    private String speed;
    @SerializedName("deg")
    private String degree;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
