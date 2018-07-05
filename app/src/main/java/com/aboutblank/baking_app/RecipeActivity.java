package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.IngredientListFragment;
import com.aboutblank.baking_app.view.RecipeFragment;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    IngredientListFragment ingredientListFragment;
    RecipeFragment recipeFragment;

    private MainViewModel mainViewModel;
    private LiveData<Recipe> recipe;
    private int recipeId = -1;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        compositeDisposable = new CompositeDisposable();

        ingredientListFragment = (IngredientListFragment) getSupportFragmentManager().findFragmentById(R.id.ingredient_list);
        ingredientListFragment.setCompositeDisposable(compositeDisposable);

        recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_steps);
        recipeFragment.setCompositeDisposable(compositeDisposable);

        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

        if (getIntent() != null) {
            recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

            Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));

            recipe = mainViewModel.getRecipe(recipeId);

            recipeFragment.setRecipe(recipe);

            recipe.observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        ingredientListFragment.setRecipe(recipe);
                    }
                }
            });

        } else {
            Log.e(LOG_TAG, "Launching intent must have a recipe id");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
