package com.aboutblank.baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.widget.RemoteViews;

import com.aboutblank.baking_app.DetailActivity;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.MinimalRecipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {
    private static SparseArray<MinimalRecipe> widgetIdMap = new SparseArray<>();

    static void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void createAppWidget(Context context, AppWidgetManager appWidgetManager,
                                MinimalRecipe minimalRecipe, int appWidgetId) {
        widgetIdMap.append(appWidgetId, minimalRecipe);
        updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d("WidgetIdMap", widgetIdMap.toString());

        MinimalRecipe minimalRecipe = widgetIdMap.get(appWidgetId);

        // Construct the List view adapter intent
        Intent intent = new Intent(context, IngredientListViewWidgetService.class);
        intent.putExtra("id", minimalRecipe.getId());

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
        views.setRemoteAdapter(R.id.widget_list, intent);
        views.setTextViewText(R.id.widget_title, minimalRecipe.getName());

        //Set pending intent to launch DetailActivity
        Intent appIntent = new Intent(context, DetailActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_list, appPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("WidgetProvider", "onUpdate");

        IngredientListIntentService.startActionUpdateListWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d("WidgetProvider", "onDeleted");
        for(int id : appWidgetIds) {
            widgetIdMap.remove(id);
        }
    }
}

