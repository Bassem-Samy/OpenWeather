package com.kbm.openweather.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Bassem on 7/27/2017.
 */

public class LocationStatusHelper {

    public static boolean checkIfLocationIsEnabled(Context mContext) {
        int locationMode = Settings.Secure.getInt(
                mContext.getContentResolver(),
                Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF // Default value if not
        );
        if (locationMode == Settings.Secure.LOCATION_MODE_OFF) {
            // Location is off
            return false;
        }
        return true;
    }
}
