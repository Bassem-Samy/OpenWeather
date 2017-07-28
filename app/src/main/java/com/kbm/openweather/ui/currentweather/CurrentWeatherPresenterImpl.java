package com.kbm.openweather.ui.currentweather;

import android.content.Context;

import com.kbm.openweather.models.CurrentWeatherResponse;
import com.kbm.openweather.utils.NetworkStatusHelper;

import java.lang.ref.WeakReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bassem on 7/28/2017.
 */

public class CurrentWeatherPresenterImpl implements CurrentWeatherPresenter {
    private CurrentWeatherView mView;
    private CurrentWeatherInteractor mInteractor;
    private Disposable mRequestDisposable;
    private WeakReference<Context> mContextWeakReference;

    public CurrentWeatherPresenterImpl(CurrentWeatherView view, CurrentWeatherInteractor interactor, Context context) {
        this.mView = view;
        this.mInteractor = interactor;
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    public void getCurrentWeatherByLocation(String appId, String latitude, String longitude) {
        disposeRequest();
        if (!NetworkStatusHelper.checkInternetAvailable(mContextWeakReference.get())) {
            mView.showProgress();
            return;
        }
        mView.showProgress();
        mRequestDisposable = mInteractor.getCurrentWeatherByLocation(appId, latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CurrentWeatherResponse>() {
                    @Override
                    public void accept(@NonNull CurrentWeatherResponse currentWeatherResponse) throws Exception {
                        mView.hideProgress();
                        if (currentWeatherResponse != null) {
                            mView.updateData(currentWeatherResponse);
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
