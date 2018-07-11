package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.data.IDataModel;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

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

    public Observable<Set<Integer>> getIndexedIngredients(int recipeIndex) {
        return Observable.just(dataModel.getIndexedIngredients(recipeIndex));
    }
}
