package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.RemoteRepository;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

@Singleton
public class DataModel implements IDataModel {
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private LocalRepository localRepository;
    @NonNull
    private RemoteRepository remoteRepository;
    @NonNull
    private ISchedulerProvider schedulerProvider;
    @NonNull
    private CompositeDisposable compositeDisposable;

    private LiveData<List<MinimalRecipe>> minimalRecipes;
    private LiveData<Recipe> currentRecipe;

    @Inject
    public DataModel(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository,
                     @NonNull ISchedulerProvider schedulerProvider, @NonNull CompositeDisposable compositeDisposable) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void update() {
        Observable<List<Recipe>> recipes = remoteRepository.getRecipes();

        compositeDisposable.add(recipes.observeOn(schedulerProvider.computation())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(List<Recipe> recipes) throws Exception {
                        localRepository.insertRecipes(recipes.toArray(new Recipe[0]));
                    }
                }));
    }

    @Override
    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        if (minimalRecipes == null) {
            minimalRecipes = localRepository.getMinimalRecipes();
        }
        return minimalRecipes;
    }

    @Override
    public LiveData<Recipe> getRecipe(int id) {
        if (currentRecipe == null) {
            currentRecipe = localRepository.getRecipe(id);
        }
        return currentRecipe;
    }
}
