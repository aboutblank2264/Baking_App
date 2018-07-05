package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.aboutblank.baking_app.utils.ImageUtils;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private IDataModel dataModel;

    @NonNull
    private ISchedulerProvider schedulerProvider;

    @NonNull
    private ImageUtils imageUtils;

    @NonNull
    private MediaPlayerPool mediaPlayerPool;

    private LiveData<List<MinimalRecipe>> minimalRecipes;

    private int currentId;
    private LiveData<Recipe> currentRecipe;

    @Inject
    public MainViewModel(@NonNull DataModel dataModel, @NonNull ISchedulerProvider schedulerProvider,
                         @NonNull ImageUtils imageUtils, @NonNull MediaPlayerPool mediaPlayerPool) {
        this.dataModel = dataModel;
        this.schedulerProvider = schedulerProvider;
        this.imageUtils = imageUtils;
        this.mediaPlayerPool = mediaPlayerPool;
    }

    public void update() {
        //TODO DataModel returns an observable to subscribe to for error reporting.
        dataModel.update();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        if (minimalRecipes == null) {
            minimalRecipes = dataModel.getMinimalRecipes();
        }
        return minimalRecipes;
    }

    public LiveData<Recipe> getRecipe(int id) {
        if (currentId != id || currentRecipe == null) {
            currentRecipe = dataModel.getRecipe(id);
            currentId = id;
        }
        return currentRecipe;
    }

    @NonNull
    public ImageUtils getImageUtils() {
        return imageUtils;
    }

    public void indexIngredient(int recipeIndex, int ingredientIndex) {
        dataModel.indexIngredient(recipeIndex, ingredientIndex);
    }

    public Set<Integer> getIndexedIngredients(int recipeIndex) {
        return dataModel.getIndexedIngredients(recipeIndex);
    }

    @NonNull
    public Single<MediaPlayer> getPlayer() {
        return mediaPlayerPool.getPlayer().subscribeOn(schedulerProvider.computation());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mediaPlayerPool.cleanup();
    }
}
