package com.aboutblank.baking_app.usecases;

import android.arch.lifecycle.LiveData;

import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoadRecipesUseCase {
    private final IDataModel dataModel;

    @Inject
    public LoadRecipesUseCase(IDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipe() {
        return dataModel.getMinimalRecipes();
    }

    public LiveData<Recipe> getRecipe(int id) {
        return dataModel.getRecipe(id);
    }

    public void clearLocalDatabase() {
        dataModel.clear();
    }
}
