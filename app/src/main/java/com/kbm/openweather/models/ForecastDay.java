package com.kbm.openweather.models;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastDay implements Comparable<ForecastDay> {
    private String dateText;
    private Date date;

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ForecastItem> getForecastItems() {
        return forecastItems;
    }

    public void setForecastItems(List<ForecastItem> forecastItems) {
        this.forecastItems = forecastItems;
    }

    private List<ForecastItem> forecastItems;


    @Override
    public int compareTo(@NonNull ForecastDay item) {
        if (this.date.getTime() > item.getDate().getTime())
            return 1;
        if (this.date.getTime() == item.getDate().getTime())
            return 0;
        return -1;
    }
}
