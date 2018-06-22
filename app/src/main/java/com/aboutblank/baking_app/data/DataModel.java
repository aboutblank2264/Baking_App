package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.RemoteRepository;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Singleton
public class DataModel implements IDataModel {
    private final String LOG_TAG = getClass().getSimpleName();

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private ISchedulerProvider schedulerProvider;

    private LiveData<List<Recipe>> fullRecipeListCache;

    @Inject
    public DataModel(LocalRepository localRepository, RemoteRepository remoteRepository, ISchedulerProvider schedulerProvider) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<String> update() {
        remoteRepository.getRecipes()
                .subscribeOn(schedulerProvider.computation())
                .subscribe(new Observer<List<Recipe>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Recipe> recipes) {
//                Log.d(LOG_TAG, recipes.toString());
                        localRepository.insertRecipes(recipes.toArray(new Recipe[0]));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return null;
    }

    private void getRecipesOnNext() {

    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        if (fullRecipeListCache == null) {
            fullRecipeListCache = localRepository.getRecipes();
        }

        return null;
    }
}
