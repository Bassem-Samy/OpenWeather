package com.kbm.openweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastItem implements Parcelable {
    protected ForecastItem(Parcel in) {
        dateTime = in.readString();
        wind = in.readParcelable(Wind.class.getClassLoader());
        watherSys = in.readParcelable(WeatherSys.class.getClassLoader());
        weatherItems = in.createTypedArrayList(WeatherItem.CREATOR);
        dateText = in.readString();
        mainWeatherInfo = in.readParcelable(MainWeatherInfo.class.getClassLoader());
    }

    public static final Creator<ForecastItem> CREATOR = new Creator<ForecastItem>() {
        @Override
        public ForecastItem createFromParcel(Parcel in) {
            return new ForecastItem(in);
        }

        @Override
        public ForecastItem[] newArray(int size) {
            return new ForecastItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateTime);
        parcel.writeParcelable(wind, i);
        parcel.writeParcelable(watherSys, i);
        parcel.writeTypedList(weatherItems);
        parcel.writeString(dateText);
        parcel.writeParcelable(mainWeatherInfo, i);
    }
}
