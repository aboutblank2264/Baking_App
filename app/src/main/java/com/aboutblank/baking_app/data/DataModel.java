package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.RemoteRepository;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private SparseArray<Set<Integer>> ownedRecipeIngredientsMap;

    @Inject
    public DataModel(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository,
                     @NonNull ISchedulerProvider schedulerProvider, @NonNull CompositeDisposable compositeDisposable) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;

        ownedRecipeIngredientsMap = new SparseArray<>();
    }

    @Override
    public void update() {
        Observable<List<Recipe>> recipes = remoteRepository.getRecipes();

        compositeDisposable.add(recipes.observeOn(schedulerProvider.computation())
                .subscribeOn(schedulerProvider.computation())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(List<Recipe> recipes) {
                        localRepository.insertRecipes(recipes.toArray(new Recipe[0]));
                    }
                }));
    }

    @Override
    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return localRepository.getMinimalRecipes();
    }

    @Override
    public LiveData<Recipe> getRecipe(int id) {
        return localRepository.getRecipe(id);
    }

    //TODO return an Observable
    @Override
    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        Set<Integer> ingredientList = ownedRecipeIngredientsMap.get(recipeIndex);

        if (ingredientList == null) {
            ingredientList = new HashSet<>();
        }

        if (ingredientList.contains(ingredientIndex)) {
            ingredientList.remove(ingredientIndex);
        } else {
            ingredientList.add(ingredientIndex);
        }
    }

    //TODO return an Observable
    @Override
    public Set<Integer> getIndexedIngredients(int recipeIndex) {
        return ownedRecipeIngredientsMap.get(recipeIndex);
    }

    @Override
    public void clear() {
        localRepository.deleteAll();
    }
}
