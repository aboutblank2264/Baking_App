package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.fragments.RecipeFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeActivity extends AppCompatActivity implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private RecipeFragment recipeFragment;

    private CompositeDisposable compositeDisposable;
    private RecipeViewModel recipeViewModel;
    private RecipeViewState recipeViewState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeFragment = (RecipeFragment) getSupportFragmentManager().findFragmentById(R.id.recipe_fragment);

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

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
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
                setState(new RecipeViewState.Builder(recipe).build());
            }
        });
    }

    private void setState(RecipeViewState state) {
        recipeViewState = state;
        recipeFragment.setState(state);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(LOG_TAG, "Loading detail view with position " + position);
        getRecipeViewModel().changeToDetailView(this, recipeViewState.getRecipe().getId(), position);
    }
}
