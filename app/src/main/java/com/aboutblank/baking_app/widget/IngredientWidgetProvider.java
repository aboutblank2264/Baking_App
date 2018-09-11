package com.aboutblank.baking_app.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.MinimalRecipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                MinimalRecipe minimalRecipe, int appWidgetId) {
        // Construct the List view adapter intent
        Intent intent = getIntent(context, minimalRecipe.getId());

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
        views.setRemoteAdapter(R.id.widge_list, intent);
        views.setTextViewText(R.id.widget_title, minimalRecipe.getName());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, IngredientListViewWidgetService.class);
        intent.putExtra("id", id);
        return intent;
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        MinimalRecipe title, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, title, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

