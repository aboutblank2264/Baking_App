package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.fragments.DetailFragment;
import com.aboutblank.baking_app.view.fragments.RecipeFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private RecipeFragment recipeFragment;

    private RecipeViewModel recipeViewModel;
    private RecipeViewState recipeViewState;

    private boolean tabletLayout = false;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setSupportActionBar(findViewById(R.id.recipe_toolbar));

        recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_fragment);

        //check if is a tablet layout or not.
        tabletLayout = findViewById(R.id.recipe_detail_fragment) != null;

        if(tabletLayout) {
            Log.d(LOG_TAG, "Detecting tablet view, switching to appropriate layout");
            detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_detail_fragment);

//            if(savedInstanceState != null) {
//                detailFragment.setArguments(savedInstanceState);
//            }
        }

        if (getIntent() != null) {
            int recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

            if (recipeId <= 0) {
                throw new IllegalArgumentException("Unable to load recipe, recipe Id not properly set, was given: " + recipeId);
            } else {
                Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));
                observeRecipe(recipeId);
            }
        } else {
            throw new IllegalArgumentException("Unable to load recipe, recipe Id not properly set, no recipe id was given");
        }
    }

    public RecipeViewModel getRecipeViewModel() {
        if (recipeViewModel == null) {
            recipeViewModel = ((BakingApplication) getApplication()).getRecipeViewModel();
        }
        return recipeViewModel;
    }

    private void observeRecipe(int recipeId) {
        getRecipeViewModel().getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(recipe.getName());
                }
                setState(new RecipeViewState.Builder(recipe).build());
            }
        });
    }

    private void setState(RecipeViewState state) {
        recipeViewState = state;
        recipeFragment.setViewState(state);
        setDetailFragmentViewState(state);
    }

    private void setDetailFragmentViewState(RecipeViewState viewState) {
        if(tabletLayout) {
            detailFragment.setViewState(viewState);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(LOG_TAG, "Loading detail view with position " + position);
        if(tabletLayout) {
            RecipeViewState newState = new RecipeViewState.Builder(recipeViewState.getRecipe())
                    .setCurrentPosition(position)
                    .setIndexedIngredients(recipeViewState.getIndexedIngredients()).build();
            setState(newState);
        } else {
            getRecipeViewModel().changeToDetailView(this,
                    recipeViewState.getRecipe().getId(),
                    position,
                    recipeViewState.getRecipe().getName());
        }
    }
}
