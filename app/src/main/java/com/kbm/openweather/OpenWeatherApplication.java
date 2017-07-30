package com.kbm.openweather;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Bassem on 7/31/2017.
 */

public class OpenWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeLoggingTool();
    }

    private void initializeLoggingTool() {
// disable fabric in debug mode
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);
    }
}
