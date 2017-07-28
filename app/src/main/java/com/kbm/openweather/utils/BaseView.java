package com.kbm.openweather.utils;

/**
 * Created by Bassem on 7/28/2017.
 */

public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError();
    void showNoInternet();

    void showNoData();
}
