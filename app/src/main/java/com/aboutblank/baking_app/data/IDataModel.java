package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

public interface IDataModel {
    LiveData<List<MinimalRecipe>> getMinimalRecipes();

    LiveData<Recipe> getRecipe(int id);

    void indexIngredient(int recipeIndex, int ingredientIndex);

    List<Integer> getIndexedIngredients(int recipeIndex);

    void clear();
}
