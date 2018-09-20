package com.aboutblank.baking_app.widget;

import com.aboutblank.baking_app.data.model.Recipe;

public class WidgetDataModel {
//    private IDataModel dataModel;
//    private Observer<Recipe> observer;
//
//    public static WidgetDataModel makeWidgetDataModel(Context context, int id, Observer<Recipe> observer) {
//        WidgetDataModel widgetDataModel = new WidgetDataModel();
//        widgetDataModel.loadRecipe(context, id, observer);
//        return widgetDataModel;
//    }
//
//    public void loadRecipe(Context context, int id, Observer<Recipe> observer) {
//        dataModel = ((BakingApplication) context.getApplicationContext()).getDataModel();
//        dataModel.getRecipe(id).observeForever(observer);
//    }
//
//    public void cleanup(int id) {
//        if (observer != null) {
//            dataModel.getRecipe(id).removeObserver(observer);
//        }
//    }

    private Recipe recipe;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
