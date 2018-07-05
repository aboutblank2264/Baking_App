package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;
import java.util.Set;

public interface IDataModel {
    void update();

    LiveData<List<MinimalRecipe>> getMinimalRecipes();

    LiveData<Recipe> getRecipe(int id);

    void indexIngredient(int recipeIndex, int ingredientIndex);

    Set<Integer> getIndexedIngredients(int recipeIndex);

    void clear();
}
