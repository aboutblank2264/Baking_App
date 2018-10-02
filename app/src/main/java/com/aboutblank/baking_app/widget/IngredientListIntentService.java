package com.aboutblank.baking_app.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.aboutblank.baking_app.R;

public class IngredientListIntentService extends IntentService {
    public static final String ACTION_UPDATE_WIDGETS = "com.aboutblank.android.baking_app.action_update_widgets";

    public IngredientListIntentService() {
        super("IngredientListIntentService");
    }

    public static void startActionUpdateListWidgets(Context context) {
        Intent intent = new Intent(context, IngredientListIntentService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                handleUpdateListWidgets();
            }
        }
    }

    private void handleUpdateListWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
//        IngredientWidgetProvider.updateAllAppWidgets(this, appWidgetManager, appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
    }
}
