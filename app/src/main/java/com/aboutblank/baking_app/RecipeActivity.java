package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.fragments.IngredientListFragment;
import com.aboutblank.baking_app.view.fragments.RecipeFragment;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.HashSet;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeActivity extends AppCompatActivity implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    RecipeFragment recipeFragment;

    private CompositeDisposable compositeDisposable;
    private RecipeViewModel recipeViewModel;

    private Recipe recipe;
    private RecipeViewState state;

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

    public void observeRecipe(int recipeId) {
        recipeViewModel.getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                this.recipe = recipe;
                setState(new RecipeViewState(recipe));
            }
        });
    }

    private void setState(RecipeViewState state) {
        this.state = state;
        recipeFragment.setState(state);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position == 0) {
            IngredientListFragment ingredientListFragment = new IngredientListFragment();
            attachFragment(ingredientListFragment);
            loadIngredientListFragment(ingredientListFragment);
        } else {
            loadStepDetailFragment(position);
        }
    }

    //TODO make animation to expand the view.

    private void loadIngredientListFragment(IngredientListFragment ingredientListFragment) {
        ingredientListFragment.setViewState(new IngredientViewState(state.getRecipe(), new HashSet<>()));
//        ingredientListFragment.subscribeToIndexedIngredients(recipeViewModel.getIndexedIngredients(state.getRecipe().getId()));

    }

    private void loadStepDetailFragment(int position) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();

    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.recipe_fragment, fragment);

        fragmentTransaction.commit();
    }
}
