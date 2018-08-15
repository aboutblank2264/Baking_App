package com.aboutblank.baking_app.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;

import java.util.List;

public class IngredientListViewWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String title = intent.getStringExtra("title");
        List<Ingredient> ingredients = intent.getParcelableArrayListExtra("ingredients");

        return new IngredientsListView(getApplicationContext(), title, ingredients);
    }

    class IngredientsListView implements RemoteViewsService.RemoteViewsFactory {
        private Context context;
        private String title;
        private List<Ingredient> ingredientList;

        public IngredientsListView(Context context, String title, List<Ingredient> ingredientList) {
            this.context = context;
            this.title = title;
            this.ingredientList = ingredientList;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (ingredientList != null) {
                return ingredientList.size();
            } else {
                return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            views.setTextViewText(R.id.widget_ingredient_text, ingredientList.get(position).toPrint());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
