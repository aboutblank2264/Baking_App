package com.aboutblank.baking_app.data;

import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

import io.reactivex.Observable;

public interface IDataModel {
    Observable<String> update();

    Observable<List<Recipe>> getRecipes();
}
