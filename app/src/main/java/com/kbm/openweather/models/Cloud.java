package com.kbm.openweather.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class Cloud {
    @SerializedName("all")
    private String all;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
