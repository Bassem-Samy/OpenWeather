package com.kbm.openweather.ui.forecast.di;

import android.content.Context;

import com.kbm.openweather.ui.forecast.ForecastInteractor;
import com.kbm.openweather.ui.forecast.ForecastInteractorImpl;
import com.kbm.openweather.ui.forecast.ForecastPresenter;
import com.kbm.openweather.ui.forecast.ForecastPresenterImpl;
import com.kbm.openweather.ui.forecast.ForecastView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bassem on 7/29/2017.
 */
@Module
public class ForecastModule {
    private ForecastView mView;
    private Context mContext;
    private String mBaseUrl;

    public ForecastModule(ForecastView view, String baseUrl, Context context) {
        this.mView = view;
        this.mBaseUrl = baseUrl;
        this.mContext = context;
    }

    // provide a context to instantiate the presenter
    @Singleton
    @Provides
    public Context providesContext() {
        return this.mContext;
    }

    // provide the forecast view
    @Singleton
    @Provides
    public ForecastView providesForecastView() {
        return this.mView;
    }
    // provides converter factory for retrofit

    @Singleton
    @Provides
    public Converter.Factory providesConverterFactory() {
        return GsonConverterFactory.create();
    }

    // provides call adapter factory for retrofit
    @Singleton
    @Provides
    public CallAdapter.Factory providesCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    // provides a retrofit
    @Singleton
    @Provides
    public Retrofit providesRetrofit(Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .baseUrl(mBaseUrl)
                .build();
    }

    // provides a forecast interactor
    @Singleton
    @Provides
    public ForecastInteractor providesForecastInteractor(Retrofit retrofit) {
        return new ForecastInteractorImpl(retrofit);
    }

    // provides a forecast presenter
    @Singleton
    @Provides
    public ForecastPresenter providesForecastPresenter(ForecastView view, ForecastInteractor interactor, Context context) {
        return new ForecastPresenterImpl(view, interactor, context);
    }
}
