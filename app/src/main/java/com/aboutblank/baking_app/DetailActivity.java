package com.aboutblank.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.fragments.IngredientListFragment;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends AppCompatActivity {
    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.fragment_placeholder)
    View fragment_placeholder;

    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;

    private int position;
    private Recipe recipe;
    private RecipeViewState state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(getString(R.string.position), -1);
            if (position > -1) {
                int recipeId = intent.getIntExtra(getString(R.string.intent_recipe_id), -1);

                if (recipeId <= 0) {
                    throw new IllegalArgumentException("Unable to load recipe, recipe Id not properly set, was given: " + recipeId);
                } else {
                    Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));
                    observeRecipe(recipeId);
                }
            } else {
                throw new IllegalArgumentException("Unable to load details, position not properly set.");
            }
        } else {
            throw new IllegalArgumentException("Unable to load recipe, recipe Id not properly set, no recipe id was given");
        }
    }

    public void observeRecipe(int recipeId) {
        getRecipeViewModel().getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                this.recipe = recipe;
                setState(new RecipeViewState(recipe));
            }
        });
    }

    public RecipeViewModel getRecipeViewModel() {
        if (recipeViewModel == null) {
            recipeViewModel = ((BakingApplication) getApplication()).getRecipeViewModel();
        }
        return recipeViewModel;
    }

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    private void setState(RecipeViewState state) {
        this.state = state;
        loadFragment(position);
    }

    private void loadFragment(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = loadIngredientListFragment();
        } else {
            fragment = loadStepDetailFragment(position);
        }
        attachFragment(fragment);
    }

    private IngredientListFragment loadIngredientListFragment() {
        Log.d(LOG_TAG, "Loading IngredientListFragment");
        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        ingredientListFragment.setViewState(new IngredientViewState(state.getRecipe(), new HashSet<>()));
//        ingredientListFragment.subscribeToIndexedIngredients(recipeViewModel.getIndexedIngredients(state.getRecipe().getId()));
        return ingredientListFragment;
    }

    private StepDetailFragment loadStepDetailFragment(int position) {
        Log.d(LOG_TAG, "Loading StepDetailFragment");
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setRecipeViewModel(getRecipeViewModel());
        stepDetailFragment.setViewState(new DetailViewState(state.getRecipe().getSteps().get(position)));
        return stepDetailFragment;
    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);

        fragmentTransaction.commit();
    }
}
