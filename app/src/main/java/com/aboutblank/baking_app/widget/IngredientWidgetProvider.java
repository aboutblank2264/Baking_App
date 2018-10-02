package com.aboutblank.baking_app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.aboutblank.baking_app.DetailActivity;
import com.aboutblank.baking_app.R;

import java.util.Arrays;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d("WidgetProvider", String.format("Updating: RecipeId: %d, Name: %s",
                WidgetDataModel.getWidgetRecipeId(context, appWidgetId),
                WidgetDataModel.getWidgetRecipeName(context, appWidgetId)));

        // Construct the List view adapter intent
        Intent intent = new Intent(context, IngredientListViewWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //Note: setting data ensures that each intent will be sent to the applicable widget, instead of the same intent to all widgets.
        //https://stackoverflow.com/questions/28049544/multiple-widgets-with-configuration-activity
        intent.setData(Uri.withAppendedPath(Uri.parse("abc" + "://widget/id/"), String.valueOf(appWidgetId)));

        intent.putExtra(context.getString(R.string.widget_recipe_id), WidgetDataModel.getWidgetRecipeId(context, appWidgetId));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);
        views.setTextViewText(R.id.widget_title, WidgetDataModel.getWidgetRecipeName(context, appWidgetId));
        views.setRemoteAdapter(R.id.widget_list, intent);

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

        for (int id : appWidgetIds) {
            Log.d("WidgetProvider", "widgetId: " + id);
            updateAppWidget(context, appWidgetManager, id);
        }
    }

    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        Log.d("WidgetProvider", "onReceive");

        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        Log.d("WidgetProvider", action);

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            if (extras != null) {
                int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);

                Log.d("WidgetProvider", "AppWidgetIds: " + Arrays.toString(appWidgetIds));

                if (appWidgetIds != null && appWidgetIds.length > 0) {
                    for (int appWidgetId : appWidgetIds) {
                        // if this is a valid fully formed widget, on creating a new widget, there is a null set widget that triggers update.
                        if (extras.getInt(context.getString(R.string.widget_app_id) + appWidgetId) != 0 &&
                                extras.getString(context.getString(R.string.widget_name) + appWidgetId) != null) {
                            Log.d("WidgetProvider", "saving new widget");
                            Log.d("WidgetProvider", String.format("Name: %s, Id %d",
                                    extras.getString(context.getString(R.string.widget_name) + appWidgetId),
                                    extras.getInt(context.getString(R.string.widget_app_id) + appWidgetId)));

                            WidgetDataModel.saveWidgetRecipeId(context, appWidgetId,
                                    extras.getInt(context.getString(R.string.widget_app_id) + appWidgetId));
                            WidgetDataModel.saveWidgetRecipeName(context, appWidgetId,
                                    extras.getString(context.getString(R.string.widget_name) + appWidgetId));

                            updateAppWidget(context, AppWidgetManager.getInstance(context), appWidgetId);
                        }
                        Log.d("WidgetData", String.format("Name: %s, Id %d",
                                WidgetDataModel.getWidgetRecipeName(context, appWidgetId),
                                WidgetDataModel.getWidgetRecipeId(context, appWidgetId)));
                    }
//                    this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
                }
            }
        } else if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
            if (extras != null && extras.containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                final int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
                this.onDeleted(context, new int[]{appWidgetId});
            }
        } else if (AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)) {
            this.onEnabled(context);
        } else if (AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action)) {
            this.onDisabled(context);
        }
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
        for (int id : appWidgetIds) {
            Log.d("WidgetProvider", "onDeleted");
            Log.d("WidgetProvider", Arrays.toString(appWidgetIds));
            WidgetDataModel.deleteWidget(context, id);
        }
    }
}

