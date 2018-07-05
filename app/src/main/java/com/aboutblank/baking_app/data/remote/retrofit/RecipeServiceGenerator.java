package com.aboutblank.baking_app.data.remote.retrofit;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class RecipeServiceGenerator {
    private final static String BASE_URL = "http://go.udacity.com";

    private final static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC);

    private final static OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build();

    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Singleton
    @Provides
    public static RecipeService getRecipeService() {
        return retrofit.create(RecipeService.class);
    }
}