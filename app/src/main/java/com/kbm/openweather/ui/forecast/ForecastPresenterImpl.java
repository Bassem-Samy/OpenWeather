package com.kbm.openweather.ui.forecast;

import android.content.Context;

import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastItem;
import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.utils.ForecastToDaysConverter;
import com.kbm.openweather.utils.NetworkStatusHelper;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem on 7/29/2017.
 */

public class ForecastPresenterImpl implements ForecastPresenter {
    private ForecastView mView;
    private ForecastInteractor mInteractor;
    private Disposable mRequestDisposable;
    private WeakReference<Context> mContextWeakReference;
    private SimpleDateFormat mSimpleDateFormat;

    public ForecastPresenterImpl(ForecastView view, ForecastInteractor interactor, Context context) {
        this.mView = view;
        this.mInteractor = interactor;
        this.mContextWeakReference = new WeakReference<>(context);

    }

    @Override
    public void getForecast(String appId, String latitude, String longitude, String unit) {
        disposeRequest();
        if (!NetworkStatusHelper.checkInternetAvailable(mContextWeakReference.get())) {
            mView.showNoInternet();
            return;
        }
        mView.showProgress();
        mRequestDisposable = mInteractor.getForecastByLocation(appId, latitude, longitude, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ForecastResponse, List<ForecastDay>>() {
                    @Override
                    public List<ForecastDay> apply(@NonNull ForecastResponse forecastResponse) throws Exception {

                        if (forecastResponse != null) {
                            return prepareForecastDays(forecastResponse);
                        }

                        return null;
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ForecastDay>>() {
                    @Override
                    public void accept(@NonNull List<ForecastDay> forecastDays) throws Exception {
                        mView.hideProgress();
                        if (forecastDays != null && forecastDays.size() > 0) {
                            mView.updateData(forecastDays);
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

    private List<ForecastDay> prepareForecastDays(ForecastResponse forecastResponse) {
        return ForecastToDaysConverter.getForecastDaysFromResponse(forecastResponse);
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
