package com.kbm.openweather.ui.currentweather.di;

import android.content.Context;

import com.kbm.openweather.ui.currentweather.CurrentWeatherInteractor;
import com.kbm.openweather.ui.currentweather.CurrentWeatherInteractorImpl;
import com.kbm.openweather.ui.currentweather.CurrentWeatherPresenter;
import com.kbm.openweather.ui.currentweather.CurrentWeatherPresenterImpl;
import com.kbm.openweather.ui.currentweather.CurrentWeatherView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bassem on 7/28/2017.
 */
@Module
public class CurrentWeatherModule {
    private CurrentWeatherView mView;
    private Context mContext;
    private String mBaseUrl;

    public CurrentWeatherModule(CurrentWeatherView view, String baseUrl, Context context) {
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

    // provide the current weather view
    @Singleton
    @Provides
    public CurrentWeatherView providesCurrentWeatherView() {
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

    // provides a current weather interactor
    @Singleton
    @Provides
    public CurrentWeatherInteractor providesCurrentWeatherInteractor(Retrofit retrofit) {
        return new CurrentWeatherInteractorImpl(retrofit);
    }

    // provides a current weather presenter
    @Singleton
    @Provides
    public CurrentWeatherPresenter providesCurrentWeatherPresenter(CurrentWeatherView view, CurrentWeatherInteractor interactor, Context context) {
        return new CurrentWeatherPresenterImpl(view, interactor, context);
    }
}
