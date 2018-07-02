package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.IngredientListFragment;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.RecipeFragment;

public class RecipeActivity extends AppCompatActivity implements ItemClickedListener {

    private final String LOG_TAG = getClass().getSimpleName();

    IngredientListFragment ingredientListFragment;
    RecipeFragment recipeFragment;

    private MainViewModel mainViewModel;
    private LiveData<Recipe> recipe;
    private int recipeId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ingredientListFragment = (IngredientListFragment) getSupportFragmentManager().findFragmentById(R.id.ingredient_list);

        recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_steps);
        recipeFragment.setItemClickedListener(this);

        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

        if (getIntent() != null) {
            recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

            Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));

            recipe = mainViewModel.getRecipe(recipeId);

            recipeFragment.setRecipe(recipeId, recipe);

            recipe.observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        ingredientListFragment.setIngredientList(recipe.getIngredients());
                    }
                }
            });

        } else {
            Log.e(LOG_TAG, "Launching intent must have a recipe id");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
//        List<Step> steps = recipe.getValue().getSteps();
//
//        if(steps != null && !steps.isEmpty()) {
//            loadRecipeDetailFragment(steps.get(position));
//        }
    }

//    private void loadRecipeDetailFragment(Step step) {
//        StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(step);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        fragmentManager.beginTransaction()
//                .hide(fragmentManager.findFragmentById(R.id.recipe_steps))
//                .commit();
//
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragment_placeholder, stepDetailFragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit();
//    }
}
