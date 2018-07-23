package com.aboutblank.baking_app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.ImageView;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.usecases.ChangeViewUseCase;
import com.aboutblank.baking_app.usecases.LoadImageUseCase;
import com.aboutblank.baking_app.usecases.LoadIngredientsUseCase;
import com.aboutblank.baking_app.usecases.LoadMediaPlayerUseCase;
import com.aboutblank.baking_app.usecases.LoadRecipesUseCase;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RecipeViewModel extends ViewModel {
    private LoadIngredientsUseCase loadIngredientsUseCase;
    private LoadRecipesUseCase loadRecipesUseCase;
    private LoadImageUseCase loadImageUseCase;
    private ChangeViewUseCase changeViewUseCase;
    private LoadMediaPlayerUseCase loadMediaPlayerUseCase;

    @Inject
    public RecipeViewModel(LoadIngredientsUseCase loadIngredientsUseCase,
                           LoadRecipesUseCase loadRecipesUseCase,
                           LoadImageUseCase loadImageUseCase,
                           ChangeViewUseCase changeViewUseCase,
                           LoadMediaPlayerUseCase loadMediaPlayerUseCase) {
        this.loadIngredientsUseCase = loadIngredientsUseCase;
        this.loadRecipesUseCase = loadRecipesUseCase;
        this.loadImageUseCase = loadImageUseCase;
        this.changeViewUseCase = changeViewUseCase;
        this.loadMediaPlayerUseCase = loadMediaPlayerUseCase;
    }

    public void changeToRecipeView(Context context, int recipeId) {
        changeViewUseCase.startRecipeActivity(context, recipeId);
    }

    public void loadImage(ImageView imageView, String imageUrl) {
        loadImageUseCase.loadImage(imageView, imageUrl);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loadMediaPlayerUseCase.clear();
    }

    public Observable<Set<Integer>> getIndexedIngredients(int recipeId) {
        return loadIngredientsUseCase.getIndexedIngredients(recipeId);
    }

    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        loadIngredientsUseCase.indexIngredient(recipeIndex, ingredientIndex);
    }

    public LiveData<Recipe> getRecipe(int recipeId) {
        return loadRecipesUseCase.getRecipe(recipeId);
    }
}
