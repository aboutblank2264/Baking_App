package com.aboutblank.baking_app.di.components;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.remote.retrofit.RecipeServiceGenerator;
import com.aboutblank.baking_app.di.modules.ApplicationModule;
import com.aboutblank.baking_app.di.modules.ContextModule;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, RecipeServiceGenerator.class})
public interface ApplicationComponent {
    IDataModel dataModel();
    ISchedulerProvider schedulerProvider();
    MainViewModel mainViewModel();
    CompositeDisposable compositeDisposable();
}
