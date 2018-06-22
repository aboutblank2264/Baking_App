package com.aboutblank.baking_app.data;

import android.arch.lifecycle.LiveData;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

public interface IDataModel {
    void update();

    LiveData<List<MinimalRecipe>> getMinimalRecipes();

    LiveData<Recipe> getRecipe(int id);
}
