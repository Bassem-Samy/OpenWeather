package com.kbm.openweather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bassem on 7/28/2017.
 */

public class Cloud implements Parcelable {
    @SerializedName("all")
    private String all;

    protected Cloud(Parcel in) {
        all = in.readString();
    }

    public static final Creator<Cloud> CREATOR = new Creator<Cloud>() {
        @Override
        public Cloud createFromParcel(Parcel in) {
            return new Cloud(in);
        }

        @Override
        public Cloud[] newArray(int size) {
            return new Cloud[size];
        }
    };

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(all);
    }
}
