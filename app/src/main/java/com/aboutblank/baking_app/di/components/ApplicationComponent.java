package com.aboutblank.baking_app.di.components;

import android.content.Context;

import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.remote.retrofit.RecipeServiceGenerator;
import com.aboutblank.baking_app.di.modules.ApplicationModule;
import com.aboutblank.baking_app.di.modules.ContextModule;
import com.aboutblank.baking_app.di.modules.ExoPlayerModule;
import com.aboutblank.baking_app.viewmodels.MainViewModel;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, ExoPlayerModule.class,
        ApplicationModule.class, RecipeServiceGenerator.class})
public interface ApplicationComponent {
    Context context();
    IDataModel dataModel();
    MainViewModel mainViewModel();
    RecipeViewModel recipeViewModel();
}
