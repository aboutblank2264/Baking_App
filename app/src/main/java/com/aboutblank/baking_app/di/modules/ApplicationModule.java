package com.aboutblank.baking_app.di.modules;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class ApplicationModule {

    @Provides
    public static IDataModel providesDataModel(DataModel dataModel) {
        return dataModel;
    }
}
