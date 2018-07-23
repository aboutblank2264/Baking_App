package com.aboutblank.baking_app.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.usecases.ChangeViewUseCase;
import com.aboutblank.baking_app.usecases.LoadImageUseCase;
import com.aboutblank.baking_app.usecases.LoadMediaPlayerUseCase;
import com.aboutblank.baking_app.usecases.LoadRecipesUseCase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private LoadRecipesUseCase loadRecipesUseCase;
    @NonNull
    private LoadMediaPlayerUseCase loadMediaPlayerUseCase;
    @NonNull
    private ChangeViewUseCase changeViewUseCase;
    @NonNull
    private LoadImageUseCase loadImageUseCase;

    @Inject
    public MainViewModel(@NonNull LoadRecipesUseCase loadRecipesUseCase,
                         @NonNull LoadMediaPlayerUseCase loadMediaPlayerUseCase,
                         @NonNull ChangeViewUseCase changeViewUseCase,
                         @NonNull LoadImageUseCase loadImageUseCase) {
        this.loadRecipesUseCase = loadRecipesUseCase;
        this.loadMediaPlayerUseCase = loadMediaPlayerUseCase;
        this.changeViewUseCase = changeViewUseCase;
        this.loadImageUseCase = loadImageUseCase;
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
    public Single<MediaPlayer> getPlayer() {
        return loadMediaPlayerUseCase.getPlayer();
    }

    public void changeToRecipeView(Context context, int recipeId) {
        changeViewUseCase.startRecipeActivity(context, recipeId);
    }

    public void loadImage(ImageView imageView, String imageUrl) {
        loadImageUseCase.loadImage(imageView, imageUrl);
    }

    @Override
    public void onCleared() {
        super.onCleared();
        loadMediaPlayerUseCase.clear();
    }

    public void clearLocalDatabase() {
        loadRecipesUseCase.clearLocalDatabase();
    }
}
