package com.kbm.openweather.ui.currentweather;

import android.content.Context;

import com.kbm.openweather.models.CurrentWeatherDisplay;
import com.kbm.openweather.models.CurrentWeatherResponse;
import com.kbm.openweather.utils.NetworkStatusHelper;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem on 7/28/2017.
 */

public class CurrentWeatherPresenterImpl implements CurrentWeatherPresenter {
    private static final String HUMIDITY_UNIT_TEXT = " %";
    private static final String TIME_FORMAT = "hh:mm";
    private static final String TIMEZONE = "GMT";
    private static final String SPEED_UNIT_TEXT = " m/s";
    private static final String EMPTY_DEFAULT_VALUE_TEXT = "-";
    private CurrentWeatherView mView;
    private CurrentWeatherInteractor mInteractor;
    private Disposable mRequestDisposable;
    private WeakReference<Context> mContextWeakReference;
    private SimpleDateFormat mSimpleDateFormat;

    public CurrentWeatherPresenterImpl(CurrentWeatherView view, CurrentWeatherInteractor interactor, Context context) {
        this.mView = view;
        this.mInteractor = interactor;
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void getCurrentWeatherByLocation(String appId, String latitude, String longitude, String unit) {
        disposeRequest();
        if (!NetworkStatusHelper.checkInternetAvailable(mContextWeakReference.get())) {
            mView.showProgress();
            return;
        }
        mView.showProgress();
        mRequestDisposable = mInteractor.getCurrentWeatherByLocation(appId, latitude, longitude, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrentWeatherResponse>() {
                    @Override
                    public void accept(@NonNull CurrentWeatherResponse currentWeatherResponse) throws Exception {
                        mView.hideProgress();
                        if (currentWeatherResponse != null) {
                            prepareData(currentWeatherResponse);
                        } else {
                            mView.showNoData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.hideProgress();
                        mView.showError();
                    }
                });

    }

    private void prepareData(CurrentWeatherResponse currentWeatherResponse) {
        CurrentWeatherDisplay weatherDisplay = new CurrentWeatherDisplay();
        weatherDisplay.setHumidity(currentWeatherResponse.getMainWeatherInfo().getHumidity() + HUMIDITY_UNIT_TEXT);
        if (currentWeatherResponse.getWatherSys() != null) {
            weatherDisplay.setCountry(currentWeatherResponse.getWatherSys().getCountry());
            weatherDisplay.setSunrise(prepareDate(currentWeatherResponse.getWatherSys().getSunrise()));
            weatherDisplay.setSunset(prepareDate(currentWeatherResponse.getWatherSys().getSunset()));
        } else {
            weatherDisplay.setSunrise(EMPTY_DEFAULT_VALUE_TEXT);
            weatherDisplay.setSunset(EMPTY_DEFAULT_VALUE_TEXT);
            weatherDisplay.setCountry(EMPTY_DEFAULT_VALUE_TEXT);
        }
        weatherDisplay.setTemperature(currentWeatherResponse.getMainWeatherInfo().getTemp());
        if (currentWeatherResponse.getWind() != null) {
            weatherDisplay.setWind(currentWeatherResponse.getWind().getSpeed() + SPEED_UNIT_TEXT);
        } else {
            weatherDisplay.setWind(EMPTY_DEFAULT_VALUE_TEXT);
        }
        if (currentWeatherResponse.getWeather() != null && currentWeatherResponse.getWeather().size() > 0) {
            weatherDisplay.setMainDescription(currentWeatherResponse.getWeather().get(0).getDescription());
            weatherDisplay.setIcon(currentWeatherResponse.getWeather().get(0).getIcon());
        }

        mView.updateData(weatherDisplay);
    }

    private String prepareDate(String unix) {
        if (mSimpleDateFormat == null) {
            mSimpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
            mSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        }
        try {
            return mSimpleDateFormat.format(new Date(Long.parseLong(unix) * 1000L));
        } catch (Exception ex) {
            return EMPTY_DEFAULT_VALUE_TEXT;
        }
    }

    @Override
    public void onDestroy() {
        disposeRequest();
    }

    private void disposeRequest() {
        if (mRequestDisposable != null && !mRequestDisposable.isDisposed()) {
            mRequestDisposable.dispose();
        }
    }
}
