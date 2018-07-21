package com.aboutblank.baking_app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.di.components.ApplicationComponent;
import com.aboutblank.baking_app.di.components.DaggerApplicationComponent;
import com.aboutblank.baking_app.di.modules.ContextModule;
import com.aboutblank.baking_app.di.modules.ExoPlayerModule;
import com.aboutblank.baking_app.viewmodels.MainViewModel;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;
import com.google.android.exoplayer2.util.Util;

public class BakingApplication extends Application {
    private ApplicationComponent applicationComponent;

    public BakingApplication() {
    }

    private ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .contextModule(new ContextModule(this))
                    .exoPlayerModule(new ExoPlayerModule(Util.getUserAgent(this, getString(R.string.app_name))))
                    .build();
        }
        return applicationComponent;
    }

    @NonNull
    public MainViewModel getMainViewModel() {
        return getApplicationComponent().mainViewModel();
    }

    @NonNull
    public RecipeViewModel getRecipeViewModel() {
        return getApplicationComponent().recipeViewModel();
    }

    @NonNull
    public IDataModel getDataModel() {
        return getApplicationComponent().dataModel();
    }
}
