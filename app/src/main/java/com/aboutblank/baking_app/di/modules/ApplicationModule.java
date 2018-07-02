package com.aboutblank.baking_app.di.modules;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.aboutblank.baking_app.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public abstract class ApplicationModule {

    @Provides
    public static IDataModel providesDataModel(DataModel dataModel) {
        return dataModel;
    }

    @Provides
    public static ISchedulerProvider providesSchedulerProvider(SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }

    @Provides
    public static CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }
}
