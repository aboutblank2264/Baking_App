package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.DataModel;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;
import com.aboutblank.baking_app.utils.ExoPlayerUtils;
import com.aboutblank.baking_app.utils.ImageUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.CompositeDisposable;

@Singleton
public class MainViewModel extends ViewModel {

    @NonNull
    private IDataModel dataModel;

    @NonNull
    private ISchedulerProvider schedulerProvider;

    @NonNull
    private CompositeDisposable compositeDisposable;

    @NonNull
    private ImageUtils imageUtils;

    @NonNull
    private ExoPlayerUtils exoPlayerUtils;

    private LiveData<List<MinimalRecipe>> minimalRecipes;

    private int currentId;
    private LiveData<Recipe> currentRecipe;

    @Inject
    public MainViewModel(@NonNull DataModel dataModel, @NonNull ISchedulerProvider schedulerProvider,
                         @NonNull CompositeDisposable compositeDisposable,
                         @NonNull ImageUtils imageUtils, @NonNull ExoPlayerUtils exoPlayerUtils) {
        this.dataModel = dataModel;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        this.imageUtils = imageUtils;
        this.exoPlayerUtils = exoPlayerUtils;
    }

    public void update() {
        //TODO DataModel returns an observable to subscribe to for error reporting.
        dataModel.update();
    }

    public LiveData<List<MinimalRecipe>> getMinimalRecipes() {
        if(minimalRecipes == null) {
            minimalRecipes = dataModel.getMinimalRecipes();
        }
        return minimalRecipes;
    }

    public LiveData<Recipe> getRecipe(int id) {
        if(currentId != id || currentRecipe == null) {
            currentRecipe = dataModel.getRecipe(id);
            currentId = id;
        }
        return currentRecipe;
    }

    @NonNull
    public ImageUtils getImageUtils() {
        return  imageUtils;
    }

    @NonNull
    public ExoPlayer getExoPlayer() {
        return exoPlayerUtils.getExoPlayer();
    }

    @NonNull
    public ExtractorMediaSource.Factory getMediaSourceFactory() {
        return exoPlayerUtils.getExtractorMediaSource();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
