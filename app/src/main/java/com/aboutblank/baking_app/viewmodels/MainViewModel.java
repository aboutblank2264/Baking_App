package com.aboutblank.baking_app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.usecases.ChangeViewUseCase;
import com.aboutblank.baking_app.usecases.LoadRecipesUseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private LoadRecipesUseCase loadRecipesUseCase;
    @NonNull
    private ChangeViewUseCase changeViewUseCase;

    @Inject
    public MainViewModel(@NonNull LoadRecipesUseCase loadRecipesUseCase,
                         @NonNull ChangeViewUseCase changeViewUseCase) {
        this.loadRecipesUseCase = loadRecipesUseCase;
        this.changeViewUseCase = changeViewUseCase;
    }

    public void update() {
        loadRecipesUseCase.update();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        return loadRecipesUseCase.getMinimalRecipe();
    }

    public void changeToRecipeView(Context context, int recipeId) {
        changeViewUseCase.startRecipeActivity(context, recipeId);
    }

    @Override
    public void onCleared() {
        super.onCleared();
    }

    public void clearLocalDatabase() {
        loadRecipesUseCase.clearLocalDatabase();
    }
}
