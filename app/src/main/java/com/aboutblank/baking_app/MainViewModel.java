package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private IDataModel dataModel;

    @NonNull
    private ISchedulerProvider schedulerProvider;

    @NonNull
    private CompositeDisposable compositeDisposable;

    private LiveData<List<MinimalRecipe>> minimalRecipes;

    private int currentId;
    private LiveData<Recipe> currentRecipe;

    @Inject
    public MainViewModel(@NonNull DataModel dataModel, @NonNull ISchedulerProvider schedulerProvider,
                         @NonNull CompositeDisposable compositeDisposable) {
        this.dataModel = dataModel;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    public void update() {
        dataModel.update();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        if(minimalRecipes == null) {
            minimalRecipes = dataModel.getMinimalRecipes();
        }
        return minimalRecipes;
    }

    public LiveData<Recipe> getRecipe(int id) {
        if(currentId != id || currentRecipe == null) {
            currentRecipe = dataModel.getRecipe(id);
            currentId = id;
        }
        return currentRecipe;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
