package com.aboutblank.baking_app.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.Recipe;

class WidgetDataModel {
    private static final String PREFS_NAME_CATEGORY = "WIDGET_NAME";
    private static final String PREFS_ID_CATEGORY = "WIDGET_ID";
    public static final String INVALID_NAME = "";
    public static final int INVALID_ID = -1;

    private IDataModel dataModel;

    public WidgetDataModel(Context context) {
        dataModel = ((BakingApplication) context.getApplicationContext()).getDataModel();
    }

    public Recipe getNonLiveRecipe(int id) {
        return dataModel.getNonLiveRecipe(id);
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
}
