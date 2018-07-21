package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.RemoteRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DataModel implements IDataModel {
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private LocalRepository localRepository;
    @NonNull
    private RemoteRepository remoteRepository;
    @NonNull
    private CompositeDisposable compositeDisposable;

    private SparseArray<Set<Integer>> ownedRecipeIngredientsMap;

    @Inject
    public DataModel(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;

        compositeDisposable = new CompositeDisposable();
        ownedRecipeIngredientsMap = new SparseArray<>();
    }

    @Override
    public void update() {
        Observable<List<Recipe>> recipes = remoteRepository.getRecipes();

        compositeDisposable.add(recipes.subscribeOn(Schedulers.computation())
                .subscribe(recipes1 -> localRepository.insertRecipes(recipes1.toArray(new Recipe[0]))));
    }

    @Override
    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return localRepository.getMinimalRecipes();
    }

    @Override
    public LiveData<Recipe> getRecipe(int id) {
        return localRepository.getRecipe(id);
    }

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

    @Override
    public Observable<Set<Integer>> getIndexedIngredients(int recipeIndex) {
        Set<Integer> currentSet = ownedRecipeIngredientsMap.get(recipeIndex);
        if (currentSet == null) {
            currentSet = new HashSet<>();
            ownedRecipeIngredientsMap.append(recipeIndex, currentSet);
        }
        return Observable.just(ownedRecipeIngredientsMap.get(recipeIndex));
    }

    @Override
    public void clear() {
        localRepository.deleteAll();
    }
}
