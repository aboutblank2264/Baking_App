package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.aboutblank.baking_app.usecases.LoadIngredientsUseCase;
import com.aboutblank.baking_app.usecases.LoadMediaPlayerUseCase;
import com.aboutblank.baking_app.usecases.LoadRecipesUseCase;
import com.aboutblank.baking_app.utils.ImageUtils;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private LoadRecipesUseCase loadRecipesUseCase;
    @NonNull
    private LoadIngredientsUseCase loadIngredientsUseCase;
    @NonNull
    private LoadMediaPlayerUseCase loadMediaPlayerUseCase;
    @NonNull
    private ISchedulerProvider schedulerProvider;
    @NonNull
    private ImageUtils imageUtils;

    @Inject
    public MainViewModel(@NonNull LoadRecipesUseCase loadRecipesUseCase,
                         @NonNull LoadIngredientsUseCase loadIngredientsUseCase,
                         @NonNull LoadMediaPlayerUseCase loadMediaPlayerUseCase,
                         @NonNull ISchedulerProvider schedulerProvider,
                         @NonNull ImageUtils imageUtils) {
        this.loadRecipesUseCase = loadRecipesUseCase;
        this.loadIngredientsUseCase = loadIngredientsUseCase;
        this.loadMediaPlayerUseCase = loadMediaPlayerUseCase;
        this.schedulerProvider = schedulerProvider;
        this.imageUtils = imageUtils;
    }

    public void update() {
        loadRecipesUseCase.update();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return loadRecipesUseCase.getMinimalRecipe();
    }

    public LiveData<Recipe> getRecipe(int id) {
        return loadRecipesUseCase.getRecipe(id);
    }

    @NonNull
    public ImageUtils getImageUtils() {
        return imageUtils;
    }

    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        loadIngredientsUseCase.indexIngredient(recipeIndex, ingredientIndex);
    }

    public Observable<Set<Integer>> getIndexedIngredients(int recipeIndex) {
        return loadIngredientsUseCase.getIndexedIngredients(recipeIndex);
    }

    @NonNull
    public Single<MediaPlayer> getPlayer() {
        return loadMediaPlayerUseCase.getPlayer();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loadMediaPlayerUseCase.clear();
    }

    public void clearLocalDatabase() {
        loadRecipesUseCase.clearLocalDatabase();
    }
}
