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
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.OnSwipeListener;
import com.aboutblank.baking_app.view.fragments.BaseFragment;
import com.aboutblank.baking_app.view.fragments.IngredientListFragment;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends AppCompatActivity implements BaseActivity {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String DETAIL_FRAGMENT = "detailFragment";

    @BindView(R.id.detail_layout)
    View layout;

    @BindView(R.id.fragment_placeholder)
    View fragment_placeholder;

    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;

    private BaseFragment currentFragment;

    private RecipeViewState viewState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        int recipeId = -1;
        int position = -1;
        // If there was already a view loaded, use that one. Otherwise start initializing.
        if (savedInstanceState != null) {
            currentFragment = (BaseFragment) getSupportFragmentManager().getFragment(savedInstanceState, DETAIL_FRAGMENT);
            recipeId = savedInstanceState.getInt(getString(R.string.intent_recipe_id));
            position = savedInstanceState.getInt(getString(R.string.position));
        } else if (getIntent() != null) {
            // Make sure there is a legal position, aka the step number
            position = getIntent().getIntExtra(getString(R.string.position), -1);
            recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);
        }
        if (recipeId < 0 || position < 0) {
            throw new IllegalArgumentException(
                    String.format("Unable to load details, required properties not properly set. Given recipeId: %s, position: %s", recipeId, position));
        }

        observeRecipe(recipeId, position);
        setOnTouchListenerTo(layout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT, currentFragment);
        outState.putInt(getString(R.string.intent_recipe_id), viewState.getRecipe().getId());
        outState.putInt(getString(R.string.position), viewState.getCurrentPosition());
    }

    public void observeRecipe(int recipeId, int position) {
        getRecipeViewModel().getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                setState(new RecipeViewState.Builder(recipe)
                        .setCurrentPosition(position)
                        .build());
            }
        });
    }

    private void setOnTouchListenerTo(View view) {
        view.setOnTouchListener(new OnSwipeListener(this) {
            @Override
            public void onSwipeLeft() {
                Log.d(LOG_TAG, "onGoPrevious event triggered");
                onClickPrevious();
            }

            @Override
            public void onSwipeRight() {
                Log.d(LOG_TAG, "onGoNext event triggered");
                onClickNext();
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

    @Override
    public void setState(ViewState viewState) {
        if (viewState.getClass() == RecipeViewState.class) {
            RecipeViewState newViewState = (RecipeViewState) viewState;
            // if there is no old view or the new position is different from the old position
            if (this.viewState == null || this.viewState.getCurrentPosition() != newViewState.getCurrentPosition()) {
                // if position is the ingredient view
                if (newViewState.getCurrentPosition() == 0) {
                    currentFragment = loadIngredientListFragment(newViewState);
                } else {
                    currentFragment = loadStepDetailFragment(newViewState);
                }
            }
            this.viewState = (RecipeViewState) viewState;
            attachFragment(currentFragment);
        }
    }

    @OnClick(R.id.detail_previous)
    void onClickPrevious() {
        Log.d(LOG_TAG, "Go previous");
        recipeViewModel.goPrevious(DetailActivity.this, viewState);
    }

    @OnClick(R.id.detail_next)
    void onClickNext() {
        Log.d(LOG_TAG, "Go next");
        recipeViewModel.goNext(DetailActivity.this, viewState);
    }

    private IngredientListFragment loadIngredientListFragment(RecipeViewState state) {
        Log.d(LOG_TAG, "Loading IngredientListFragment");
        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        ingredientListFragment.setViewState(new IngredientViewState.Builder(state.getRecipe()).build());
        return ingredientListFragment;
    }

    private StepDetailFragment loadStepDetailFragment(RecipeViewState state) {
        Log.d(LOG_TAG, "Loading StepDetailFragment");
        StepDetailFragment stepDetailFragment = new StepDetailFragment();

        // reduce the current position by 1, accounting for the extra ingredients item
        stepDetailFragment.setViewState(new DetailViewState.Builder(state.getRecipe().getSteps().get(state.getCurrentPosition() - 1)).build());
        return stepDetailFragment;
    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);

        fragmentTransaction.commit();
    }
}
