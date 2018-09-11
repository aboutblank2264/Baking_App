package com.aboutblank.baking_app.data.remote;

import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.local.LocalRepository;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.remote.retrofit.RecipeService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Public class to interface with the Room implementation
@Singleton
public class RemoteRepository implements Callback<List<Recipe>> {
    private LocalRepository localRepository;

    @Inject
    public RemoteRepository(RecipeService recipeService, LocalRepository localRepository) {
        this.localRepository = localRepository;

        recipeService.getRecipes().enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
        if(response.isSuccessful() && response.body() != null) {
            List<Recipe> recipes = response.body();

            localRepository.insertRecipes(recipes.toArray(new Recipe[0]));
        }
    }

    @Override
    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        //TODO something
    }
}
