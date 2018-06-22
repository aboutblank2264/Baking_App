package com.aboutblank.baking_app.data.remote.retrofit;

import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface RecipeService {
    @Streaming
    @GET("android-baking-app-json")
    Observable<List<Recipe>> getRecipes();
}
