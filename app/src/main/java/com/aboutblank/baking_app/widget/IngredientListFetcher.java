package com.aboutblank.baking_app.widget;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.util.Log;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.data.IDataModel;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

public class IngredientListFetcher {
    private IDataModel dataModel;
    private Observer<Recipe> recipeObserver;
    private LiveData<Recipe> recipeLiveData;

    IngredientListFetcher(Context context) {
        dataModel = ((BakingApplication) context.getApplicationContext()).getDataModel();
    }

    void getRecipe(int id, List<Ingredient> ingredientList) {
        Log.d("Fetcher", String.valueOf(id));

        if(recipeObserver == null) {
            recipeObserver = recipe -> {
                if (recipe != null) {
                    ingredientList.clear();
                    ingredientList.addAll(recipe.getIngredients());
                    Log.d("Fetcher", ingredientList.toString());
                }
            };
        }
        recipeLiveData = dataModel.getRecipe(id);
        recipeLiveData.observeForever(recipeObserver);
    }
}
