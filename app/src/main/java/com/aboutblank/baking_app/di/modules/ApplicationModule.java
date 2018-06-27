package com.aboutblank.baking_app.di.modules;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.aboutblank.baking_app.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    @Provides
    public IDataModel providesDataModel(DataModel dataModel) {
        return dataModel;
    }

    @Provides
    public ISchedulerProvider providesSchedulerProvider(SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }

    @Singleton
    @Provides
    public CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}