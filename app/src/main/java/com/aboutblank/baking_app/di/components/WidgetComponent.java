package com.aboutblank.baking_app.di.components;

import android.content.Context;

import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.remote.retrofit.RecipeServiceGenerator;
import com.aboutblank.baking_app.di.modules.ApplicationModule;
import com.aboutblank.baking_app.di.modules.ContextModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, RecipeServiceGenerator.class})
public interface WidgetComponent {
    Context context();
    IDataModel dataModel();
}
