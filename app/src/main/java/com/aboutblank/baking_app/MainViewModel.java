package com.aboutblank.baking_app;

import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MainViewModel {

    @NonNull
    private IDataModel dataModel;

    @NonNull
    private ISchedulerProvider schedulerProvider;

    private Observable<List<Recipe>> cachedRecipes;

    @Inject
    public MainViewModel(@NonNull DataModel dataModel, @NonNull ISchedulerProvider schedulerProvider) {
        this.dataModel = dataModel;
        this.schedulerProvider = schedulerProvider;
    }

    public Observable<String> update() {
        return dataModel.update();
    }

    public Observable<List<Recipe>> getRecipes() {
        if(cachedRecipes == null) {

            cachedRecipes = dataModel.getRecipes();
        }
        return cachedRecipes;
    }

    public Observable<Recipe> getRecipe(int id) {
        if(cachedRecipes == null) {
        }

//        Observable.just(cachedRecipes.)
        return null;
    }

}
