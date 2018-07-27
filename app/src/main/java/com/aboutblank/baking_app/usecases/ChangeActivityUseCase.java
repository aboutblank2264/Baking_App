package com.aboutblank.baking_app.usecases;

import android.content.Context;
import android.content.Intent;

import com.aboutblank.baking_app.DetailActivity;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.RecipeActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChangeActivityUseCase {

    @Inject
    public ChangeActivityUseCase() {
    }

    public void startRecipeActivity(Context context, int recipeId) {
        Intent launchRecipeIntent = new Intent(context, RecipeActivity.class);
        launchRecipeIntent.putExtra(context.getString(R.string.intent_recipe_id), recipeId);

        context.startActivity(launchRecipeIntent);
    }

    public void startDetailActivity(Context context, int recipeId, int position) {
        Intent launchDetailIntent = new Intent(context, DetailActivity.class);
        launchDetailIntent.putExtra(context.getString(R.string.position), position);
        launchDetailIntent.putExtra(context.getString(R.string.intent_recipe_id), recipeId);

        context.startActivity(launchDetailIntent);
    }
}
