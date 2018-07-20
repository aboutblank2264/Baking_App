package com.aboutblank.baking_app.usecases;

import android.content.Context;
import android.content.Intent;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.RecipeActivity;

public class ChangeViewUseCase {


    public void startRecipeActivity(Context context, int recipeId) {

        Intent launchRecipeIntent = new Intent(context, RecipeActivity.class);
        launchRecipeIntent.putExtra(context.getString(R.string.intent_recipe_id), recipeId);

        context.startActivity(launchRecipeIntent);
    }

}
