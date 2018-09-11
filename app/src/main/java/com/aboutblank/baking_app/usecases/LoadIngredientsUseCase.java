package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.data.IDataModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoadIngredientsUseCase {
    private final IDataModel dataModel;

    @Inject
    public LoadIngredientsUseCase(IDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        dataModel.indexIngredient(recipeIndex, ingredientIndex);
    }

    public List<Integer> getIndexedIngredients(int recipeIndex) {
        return dataModel.getIndexedIngredients(recipeIndex);
    }
}
