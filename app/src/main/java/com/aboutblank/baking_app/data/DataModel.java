package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.RemoteRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataModel implements IDataModel {
    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    private LocalRepository localRepository;
    @NonNull
    private RemoteRepository remoteRepository;

    private SparseArray<List<Integer>> ownedRecipeIngredientsMap;

    @Inject
    public DataModel(@NonNull LocalRepository localRepository, @NonNull RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;

        remoteRepository.getRemoteData();

        ownedRecipeIngredientsMap = new SparseArray<>();
    }

    @Override
    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return localRepository.getMinimalRecipes();
    }

    @Override
    public LiveData<List<Recipe>> getFullRecipes() {
        return localRepository.getFullRecipes();
    }

    @Override
    public LiveData<Recipe> getRecipe(int id) {
        return localRepository.getRecipe(id);
    }

    public Recipe getNonLiveRecipe(int id) {
        return localRepository.getNonLiveRecipe(id);
    }

    @Override
    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        List<Integer> ingredientList = ownedRecipeIngredientsMap.get(recipeIndex);

        if (ingredientList == null) {
            ingredientList = new ArrayList<>();
        }

        if (ingredientList.contains(ingredientIndex)) {
            ingredientList.remove(ingredientList.indexOf(ingredientIndex));
        } else {
            ingredientList.add(ingredientIndex);
        }
    }

    @Override
    public List<Integer> getIndexedIngredients(int recipeIndex) {
        List<Integer> currentSet = ownedRecipeIngredientsMap.get(recipeIndex);
        if (currentSet == null) {
            currentSet = new ArrayList<>();
            ownedRecipeIngredientsMap.append(recipeIndex, currentSet);
        }
        return ownedRecipeIngredientsMap.get(recipeIndex);
    }

    @Override
    public void clear() {
        localRepository.deleteAll();
    }
}
