package com.aboutblank.baking_app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.di.components.ApplicationComponent;
import com.aboutblank.baking_app.di.components.DaggerApplicationComponent;
import com.aboutblank.baking_app.di.modules.ApplicationModule;
import com.aboutblank.baking_app.di.modules.ContextModule;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

public class BakingApplication extends Application {
    private ApplicationComponent applicationComponent;

    public BakingApplication() {
    }

    private ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule())
                    .contextModule(new ContextModule(this))
                    .build();
        }
        return applicationComponent;
    }

    @NonNull
    public MainViewModel getMainViewModel() {
        return getApplicationComponent().mainViewModel();
    }

    @NonNull
    public ISchedulerProvider getSchedulerProvider() {
        return getApplicationComponent().schedulerProvider();
    }

    @NonNull
    public IDataModel getDataModel() {
        return getApplicationComponent().dataModel();
    }
}
