package com.kbm.openweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class WeatherSys implements Parcelable {
    @SerializedName("message")
    private String message;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("country")
    private String country;

    protected WeatherSys(Parcel in) {
        message = in.readString();
        sunset = in.readString();
        sunrise = in.readString();
        country = in.readString();
    }

    public static final Creator<WeatherSys> CREATOR = new Creator<WeatherSys>() {
        @Override
        public WeatherSys createFromParcel(Parcel in) {
            return new WeatherSys(in);
        }

        @Override
        public WeatherSys[] newArray(int size) {
            return new WeatherSys[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeString(sunset);
        parcel.writeString(sunrise);
        parcel.writeString(country);
    }
}
