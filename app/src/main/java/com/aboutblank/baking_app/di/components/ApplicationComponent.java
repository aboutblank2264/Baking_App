package com.aboutblank.baking_app.di.components;

import android.content.Context;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.remote.retrofit.RecipeServiceGenerator;
import com.aboutblank.baking_app.di.modules.ApplicationModule;
import com.aboutblank.baking_app.di.modules.ContextModule;
import com.aboutblank.baking_app.di.modules.ExoPlayerModule;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = {ContextModule.class, ExoPlayerModule.class,
        ApplicationModule.class, RecipeServiceGenerator.class})
public interface ApplicationComponent {
    Context context();
    IDataModel dataModel();
    ISchedulerProvider schedulerProvider();
    CompositeDisposable compositeDisposable();
    ExtractorMediaSource.Factory mediaSourceFactory();
    ExoPlayer exoPlayer();
    MainViewModel mainViewModel();
}
