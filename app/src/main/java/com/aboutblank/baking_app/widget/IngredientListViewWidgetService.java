package com.aboutblank.baking_app.widget;

import android.content.Context;
import android.content.Intent;
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
        Log.d("WidgetService",
                String.valueOf(intent.getIntExtra(getApplicationContext().getString(R.string.widget_recipe_id), -1)));
        Context context = getApplicationContext();
        return new IngredientsListView(context,
                intent.getIntExtra(context.getString(R.string.widget_recipe_id), -1));
    }

    //This object is shared among all widgets. must move single widget data out into a shared object that can be referenced.
    class IngredientsListView implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private int recipeId;
        private final WidgetDataModel widgetDataModel;
        private final List<Ingredient> ingredientList;

        IngredientsListView(Context context, int recipeId) {
            this.context = context;
            this.recipeId = recipeId;
            this.ingredientList = new ArrayList<>();
            widgetDataModel = new WidgetDataModel(context, recipeId);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            Log.d("WidgetService", "onDataSetChanged");
//            widgetDataModel.getRecipe(ret -> {//issues with async nature of livedata
//                ingredientList.addAll(ret.getIngredients());
//            });
            ingredientList.addAll(widgetDataModel.getNonLiveRecipe(recipeId).getIngredients());
        }

        @Override
        public void onDestroy() {
            Log.d("WidgetService", "onDestroy");
        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d("WidgetService", "view at: " + String.valueOf(position));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            views.setTextViewText(R.id.widget_item_text_view, ingredientList.get(position).toPrint());

            Intent fillIntent = new Intent();
            fillIntent.putExtra(context.getString(R.string.position), 0);
            fillIntent.putExtra(context.getString(R.string.intent_recipe_id), recipeId);
            views.setOnClickFillInIntent(R.id.widget_item_parent_view, fillIntent);

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
