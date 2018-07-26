package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.fragments.IngredientListFragment;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends AppCompatActivity {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String DETAIL_FRAGMENT = "detailFragment";

    @BindView(R.id.fragment_placeholder)
    View fragment_placeholder;

    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;

    private Fragment currentFragment;

    private int position;
    private RecipeViewState state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        // If there was already a view loaded, use that one. Otherwise start initializing.
        if (savedInstanceState != null) {
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, DETAIL_FRAGMENT);
        } else {
            // Make sure there is a legal position, aka the step number
            position = getIntent().getIntExtra(getString(R.string.position), -1);
            if (position > -1) {
                int recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

                if (recipeId <= 0) {
                    throw new IllegalArgumentException("Unable to load recipe, recipe Id not properly set, was given: " + recipeId);
                } else {
                    Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));
                    observeRecipe(recipeId);
                }
            } else {
                throw new IllegalArgumentException("Unable to load details, position not properly set.");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT, currentFragment);
    }

    public void observeRecipe(int recipeId) {
        getRecipeViewModel().getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
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
        if (position == 0) {
            currentFragment = loadIngredientListFragment();
        } else {
            int truePosition = position - 1; // accounting for the extra ingredients item
            currentFragment = loadStepDetailFragment(truePosition);
        }
        attachFragment(currentFragment);
    }

    private IngredientListFragment loadIngredientListFragment() {
        Log.d(LOG_TAG, "Loading IngredientListFragment");
        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        ingredientListFragment.setViewState(new IngredientViewState(state.getRecipe()));
        return ingredientListFragment;
    }

    private StepDetailFragment loadStepDetailFragment(int position) {
        Log.d(LOG_TAG, "Loading StepDetailFragment");
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setViewState(new DetailViewState(state.getRecipe().getSteps().get(position)));
        return stepDetailFragment;
    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);

        fragmentTransaction.commit();
    }
}
