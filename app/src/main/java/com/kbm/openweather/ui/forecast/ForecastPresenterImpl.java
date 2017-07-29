package com.kbm.openweather.ui.forecast;

import android.content.Context;

import com.kbm.openweather.models.ForecastResponse;
import com.kbm.openweather.utils.NetworkStatusHelper;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
                .map(new Function<ForecastResponse, Object>() {
                    @Override
                    public Object apply(@NonNull ForecastResponse forecastResponse) throws Exception {
                        return null;
                    }
                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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
