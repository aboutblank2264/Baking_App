package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.RecipeFragment;

public class RecipeActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;
    private int recipeId = -1;

    private LiveData<Recipe> recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

        if(getIntent() != null) {
            recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

            recipe = mainViewModel.getRecipe(recipeId);
        } else {
            Log.e(LOG_TAG, "Launching intent must have a recipe id");
        }

    }

    private void loadRecipeDetailFragment(int position) {

        RecipeFragment recipeFragment = RecipeFragment.newInstance(position);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, recipeFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
