package com.aboutblank.baking_app.widget;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.Recipe;

public class WidgetDataModel {
    private static final String PREFS_NAME_CATEGORY = "WIDGET_NAME";
    private static final String PREFS_ID_CATEGORY = "WIDGET_ID";
    public static final String INVALID_NAME = "";
    public static final int INVALID_ID = -1;

    private IDataModel dataModel;
    private Recipe recipe;
    private int recipeId;

    public WidgetDataModel(Context context) {
        dataModel = ((BakingApplication) context.getApplicationContext()).getDataModel();
    }

    public WidgetDataModel(Context context, int recipeId) {
        dataModel = ((BakingApplication) context.getApplicationContext()).getDataModel();
        this.recipeId = recipeId;
    }

    public Recipe getNonLiveRecipe(int id) {
        return dataModel.getNonLiveRecipe(id);
    }

    public void getRecipe(DataListener<Recipe> listener) {
        dataModel.getRecipe(recipeId).observeForever(new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                listener.dataReturned(recipe);
                dataModel.getRecipe(recipeId).removeObserver(this);
            }
        });
    }
    static void saveWidgetRecipeId(Context context, int widgetId, int recipeId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ID_CATEGORY, Context.MODE_PRIVATE)
                .edit();
        editor.putInt(String.valueOf(widgetId), recipeId);
        editor.apply();
    }

    static int getWidgetRecipeId(Context context, int widgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_ID_CATEGORY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(String.valueOf(widgetId), INVALID_ID);
    }

    static void saveWidgetRecipeName(Context context, int widgetId, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME_CATEGORY, Context.MODE_PRIVATE)
                .edit();
        editor.putString(String.valueOf(widgetId), name);
        editor.apply();
    }

    static String getWidgetRecipeName(Context context, int widgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME_CATEGORY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(String.valueOf(widgetId), INVALID_NAME);
    }

    static void deleteWidget(Context context, int widgetId) {
        deleteWidgetRecipeId(context, widgetId);
        deleteWidgetRecipeName(context, widgetId);
    }

    private static void deleteWidgetRecipeId(Context context, int widgetId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_ID_CATEGORY, Context.MODE_PRIVATE)
                .edit();
        editor.remove(String.valueOf(widgetId));
        editor.apply();
    }

    private static void deleteWidgetRecipeName(Context context, int widgetId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME_CATEGORY, Context.MODE_PRIVATE)
                .edit();
        editor.remove(String.valueOf(widgetId));
        editor.apply();
    }

    public static void printAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_ID_CATEGORY, Context.MODE_PRIVATE);
        Log.d("DATA TESTING", sharedPreferences.getAll().toString());
        sharedPreferences = context.getSharedPreferences(PREFS_NAME_CATEGORY, Context.MODE_PRIVATE);
        Log.d("DATA TESTING", sharedPreferences.getAll().toString());
    }

    public interface DataListener<T> {
        void dataReturned(T ret);
    }
}
