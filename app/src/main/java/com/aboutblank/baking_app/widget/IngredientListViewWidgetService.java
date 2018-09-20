package com.aboutblank.baking_app.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("WidgetService", String.valueOf(intent.getIntExtra("id", -1)));
        return new IngredientsListView(getApplicationContext(), intent.getIntExtra("id", -1));
    }

    class IngredientsListView implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private List<Ingredient> ingredientList;
        private int id; //TODO id is not being set.
        private IngredientListFetcher ingredientListFetcher;

        IngredientsListView(Context context, int id) {
            this.context = context;
            this.id = id;
            this.ingredientList = new ArrayList<>();
            ingredientListFetcher = new IngredientListFetcher(context);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            Log.d("WidgetService", "onDataSetChanged");
            ingredientListFetcher.getRecipe(id, ingredientList);
        }

        @Override
        public void onDestroy() {
            Log.d("WidgetService", "onDestroy");
            context = null;
            ingredientListFetcher.cleanup();
        }

        @Override
        public int getCount() {
            if(ingredientList != null) {
                return ingredientList.size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d("WidgetService", ingredientList.get(position).toPrint());

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            views.setTextViewText(R.id.widget_ingredient_text, ingredientList.get(position).toPrint());

            Bundle extra = new Bundle();
            extra.putInt(context.getString(R.string.position), 0);
            extra.putInt(context.getString(R.string.intent_recipe_id), id);
            Intent fillIntent = new Intent();
            fillIntent.putExtras(extra);

            views.setOnClickFillInIntent(R.id.widget_ingredient_text, fillIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
