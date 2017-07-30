package com.kbm.openweather.ui.forecast;

import android.content.Context;

import com.kbm.openweather.models.ForecastDay;
import com.kbm.openweather.models.ForecastItem;
import com.kbm.openweather.models.ForecastResponse;
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
    private static final java.lang.String DATE_PATTERN = "yyyy-MM-dd";
    private ForecastView mView;
    private ForecastInteractor mInteractor;
    private Disposable mRequestDisposable;
    private WeakReference<Context> mContextWeakReference;
    private SimpleDateFormat mSimpleDateFormat;

    public ForecastPresenterImpl(ForecastView view, ForecastInteractor interactor, Context context) {
        this.mView = view;
        this.mInteractor = interactor;
        this.mContextWeakReference = new WeakReference<>(context);
        mSimpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
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
        List<ForecastDay> forecastDays = new ArrayList<>();
        HashMap<String, List<ForecastItem>> daysMap = new HashMap<>();
        List<ForecastItem> tempItems;
        for (ForecastItem item : forecastResponse.getForecastItems()
                ) {
            if (item.getDateText() != null) {
                String[] splits = item.getDateText().split(" ");
                if (splits.length == 2) {
                    tempItems = daysMap.get(splits[0]);
                    if (tempItems == null) {
                        tempItems = new ArrayList<>();
                    }
                    tempItems.add(item);
                    daysMap.put(splits[0], tempItems);
                }
            }
        }
        Iterator iterator = daysMap.entrySet().iterator();
        Map.Entry tempEntry;
        while (iterator.hasNext()) {
            tempEntry = (Map.Entry) iterator.next();
            ForecastDay forecastDay = new ForecastDay();
            forecastDay.setDateText((String) tempEntry.getKey());
            forecastDay.setForecastItems((List<ForecastItem>) tempEntry.getValue());
            try {
                forecastDay.setDate(mSimpleDateFormat.parse((String) tempEntry.getKey()));
            } catch (Exception ex) {
                forecastDay.setDate(new Date());
            }
            forecastDays.add(forecastDay);
            iterator.remove();
        }
        /**
         * TODO According to the requirement it should be 5 days only, but the forecast api returns the later 3 hours of the same day
         * logically it's ok to leave it display the later hours for today if want next 5 only then uncomment the following lines
         // if includes today remove it
         if (forecastDays.size() == 6) {
         forecastDays.remove(0);
         }*/
        Collections.sort(forecastDays);
        return forecastDays;
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
