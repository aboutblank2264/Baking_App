package com.aboutblank.baking_app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.ImageView;

import com.aboutblank.baking_app.BaseActivity;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.usecases.ChangeActivityUseCase;
import com.aboutblank.baking_app.usecases.ChangeRecipeStepViewUseCase;
import com.aboutblank.baking_app.usecases.LoadImageUseCase;
import com.aboutblank.baking_app.usecases.LoadIngredientsUseCase;
import com.aboutblank.baking_app.usecases.LoadMediaPlayerUseCase;
import com.aboutblank.baking_app.usecases.LoadRecipesUseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class RecipeViewModel extends ViewModel {
    private LoadIngredientsUseCase loadIngredientsUseCase;
    private LoadRecipesUseCase loadRecipesUseCase;
    private LoadImageUseCase loadImageUseCase;
    private ChangeActivityUseCase changeActivityUseCase;
    private ChangeRecipeStepViewUseCase changeRecipeStepViewUseCase;
    private LoadMediaPlayerUseCase loadMediaPlayerUseCase;

    @Inject
    public RecipeViewModel(LoadIngredientsUseCase loadIngredientsUseCase,
                           LoadRecipesUseCase loadRecipesUseCase,
                           LoadImageUseCase loadImageUseCase,
                           ChangeActivityUseCase changeActivityUseCase,
                           ChangeRecipeStepViewUseCase changeRecipeStepViewUseCase,
                           LoadMediaPlayerUseCase loadMediaPlayerUseCase) {
        this.loadIngredientsUseCase = loadIngredientsUseCase;
        this.loadRecipesUseCase = loadRecipesUseCase;
        this.loadImageUseCase = loadImageUseCase;
        this.changeActivityUseCase = changeActivityUseCase;
        this.changeRecipeStepViewUseCase = changeRecipeStepViewUseCase;
        this.loadMediaPlayerUseCase = loadMediaPlayerUseCase;
    }

    public void changeToDetailView(Context context, int recipeId, int position) {
        changeActivityUseCase.startDetailActivity(context, recipeId, position);
    }

    public Single<MediaPlayer> getPlayer(boolean samePlayer) {
        return loadMediaPlayerUseCase.getPlayer(samePlayer);
    }

    public void loadImage(ImageView imageView, String imageUrl) {
        loadImageUseCase.loadImage(imageView, imageUrl);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        loadMediaPlayerUseCase.clear();
    }

    public Observable<List<Integer>> getIndexedIngredients(int recipeId) {
        return loadIngredientsUseCase.getIndexedIngredients(recipeId);
    }

    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        loadIngredientsUseCase.indexIngredient(recipeIndex, ingredientIndex);
    }

    public LiveData<Recipe> getRecipe(int recipeId) {
        return loadRecipesUseCase.getRecipe(recipeId);
    }

    public void goNext(BaseActivity activity, RecipeViewState state) {
        changeRecipeStepViewUseCase.onGoNext(activity, state);
    }

    public void goPrevious(BaseActivity activity, RecipeViewState state) {
        changeRecipeStepViewUseCase.onGoPrevious(activity, state);
    }
}
