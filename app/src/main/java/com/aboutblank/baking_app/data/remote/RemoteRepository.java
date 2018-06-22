package com.aboutblank.baking_app.data.remote;

import com.aboutblank.baking_app.data.remote.retrofit.RecipeService;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

// Public class to interface with the Room implementation
@Singleton
public class RemoteRepository {
    private RecipeService recipeService;

    @Inject
    public RemoteRepository(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public Observable<List<Recipe>> getRecipes() {
        return recipeService.getRecipes();
    }
}
