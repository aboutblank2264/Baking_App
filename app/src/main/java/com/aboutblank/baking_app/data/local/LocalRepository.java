package com.aboutblank.baking_app.data.local;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aboutblank.baking_app.data.local.room.RecipeDao;
import com.aboutblank.baking_app.data.local.room.RecipeDatabase;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

// Public class to interface with the Room implementation
@Singleton
public class LocalRepository {
    private RecipeDao recipeDao;

    @Inject
    public LocalRepository(Context context) {
        this.recipeDao = RecipeDatabase.getInstance(context).getRecipeDao();
    }

    public LiveData<List<Recipe>> getFullRecipes() {
        return recipeDao.getRecipes();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return recipeDao.getMinimalRecipes();
    }

    public LiveData<Recipe> getRecipe(int id) {
        return recipeDao.getRecipe(id);
    }

    public void insertRecipes(Recipe... recipes) {
        recipeDao.insert(recipes);
    }

    public void updateRecipe(@NonNull Recipe recipe) {
        recipeDao.update(recipe);
    }

    public void deleteRecipe(Recipe... recipes) {
        recipeDao.delete(recipes);
    }

    public void deleteAll() {
        AsyncTask.execute(() -> {
            Log.d("TESTING", "Deleting local database");
            recipeDao.deleteAll();
        });
    }
}
