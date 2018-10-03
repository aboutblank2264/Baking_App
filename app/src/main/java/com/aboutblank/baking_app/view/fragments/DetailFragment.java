package com.aboutblank.baking_app.view.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.OnSwipeListener;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailFragment extends BaseFragment {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String DETAIL_FRAGMENT = "detailFragment";

    @BindView(R.id.fragment_detail_layout)
    View layout;

    @BindView(R.id.fragment_placeholder)
    View fragment_placeholder;

    private RecipeViewModel recipeViewModel;

    private BaseFragment currentFragment;

    private RecipeViewState viewState;

    private int recipeId = -1;
    private int position = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, String.valueOf(requireActivity().getIntent() != null));
        Log.d(LOG_TAG, String.valueOf(getArguments() != null));

        if (requireActivity().getIntent() != null) {
            // Make sure there is a legal position, aka the step number
            position = requireActivity().getIntent().getIntExtra(getString(R.string.position), -1);
            recipeId = requireActivity().getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);
        }
        if (recipeId < 0 || position < 0) {
            throw new IllegalArgumentException(
                    String.format("Unable to load details, required properties not properly set. Given recipeId: %s, position: %s", recipeId, position));
        }

//        observeRecipe(recipeId, position);
//        setOnTouchListenerTo(layout);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        observeRecipe(recipeId, position);
        setOnTouchListenerTo(layout);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        currentFragment.saveFragment(requireFragmentManager());
        outState.putInt(getString(R.string.intent_recipe_id), viewState.getRecipe().getId());
        outState.putInt(getString(R.string.position), viewState.getCurrentPosition());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            recipeId = savedInstanceState.getInt(getString(R.string.intent_recipe_id));
            position = savedInstanceState.getInt(getString(R.string.position));
        }
    }

    public void observeRecipe(int recipeId, int position) {
        getRecipeViewModel().getRecipe(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                setViewState(new RecipeViewState.Builder(recipe)
                        .setCurrentPosition(position)
                        .build());
            }
        });
    }

    private void setOnTouchListenerTo(View view) {
        view.setOnTouchListener(new OnSwipeListener(requireContext()) {
            @Override
            public void onSwipeLeft() {
                Log.d(LOG_TAG, "onGoNext event triggered");
                onClickNext();
            }

            @Override
            public void onSwipeRight() {
                Log.d(LOG_TAG, "onGoPrevious event triggered");
                onClickPrevious();
            }
        });
    }

    public RecipeViewModel getRecipeViewModel() {
        if (recipeViewModel == null) {
            recipeViewModel = ((BakingApplication) requireActivity().getApplication()).getRecipeViewModel();
        }
        return recipeViewModel;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void saveFragment(FragmentManager fragmentManager) {

    }

    @Override
    public void setViewState(ViewState viewState) {
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
        setViewState(getRecipeViewModel().goPrevious(viewState));
    }

    @OnClick(R.id.detail_next)
    void onClickNext() {
        Log.d(LOG_TAG, "Go next");
        setViewState(getRecipeViewModel().goNext(viewState));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("onConfigurationChanged", String.valueOf(newConfig.orientation));
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE
                && currentFragment.getClass().equals(StepDetailFragment.class)
                && ((StepDetailFragment) currentFragment).hasVideo()) {

            getRecipeViewModel().showDialog(requireFragmentManager(), (StepDetailFragment) currentFragment);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT
                && currentFragment.getClass().equals(StepDetailFragment.class)) {
            getRecipeViewModel().dismissDialog();
        }
        super.onConfigurationChanged(newConfig);
    }

    private BaseFragment loadIngredientListFragment(RecipeViewState state) {
        Log.d(LOG_TAG, "Loading IngredientListFragment");
        Fragment ingredientListFragment = requireFragmentManager().findFragmentByTag(IngredientListFragment.INGREDIENT_LIST_FRAGMENT_TAG);
        if (ingredientListFragment == null) {
            ingredientListFragment = new IngredientListFragment();
        }
        ((BaseFragment) ingredientListFragment).setViewState(new IngredientViewState.Builder(state.getRecipe()).build());
        return ((BaseFragment) ingredientListFragment);
    }

    private BaseFragment loadStepDetailFragment(RecipeViewState state) {
        Log.d(LOG_TAG, "Loading StepDetailFragment");
        Fragment stepDetailFragment = requireFragmentManager().findFragmentByTag(StepDetailFragment.STEP_DETAIL_FRAGMENT_TAG);
        if (stepDetailFragment == null) {
            stepDetailFragment = new StepDetailFragment();
        }
        // reduce the current position by 1, accounting for the extra ingredients item
        ((BaseFragment) stepDetailFragment).setViewState(new DetailViewState.Builder(state.getRecipe().getSteps().get(state.getCurrentPosition() - 1)).build());
        return ((BaseFragment) stepDetailFragment);
    }

    private void attachFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = requireFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_placeholder, fragment);

        fragmentTransaction.commit();
    }
}
