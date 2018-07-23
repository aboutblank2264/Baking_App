package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.view.ParentView;
import com.aboutblank.baking_app.view.adapters.IRecipeViewHolder;
import com.aboutblank.baking_app.view.adapters.RecipeRecyclerViewAdapter;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeActivity extends AppCompatActivity implements ParentView {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.recipe_recycler)
    RecyclerView recipeRecyclerView;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

    private RecipeViewModel recipeViewModel;
    private LiveData<Recipe> recipe;

    private CompositeDisposable compositeDisposable;

    private RecyclerView.SmoothScroller smoothScroller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();

        recipeViewModel = ((BakingApplication) getApplication()).getRecipeViewModel();
        setupRecyclerView(recipeViewModel, compositeDisposable);

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

    private void setupRecyclerView(@NonNull RecipeViewModel recipeViewModel, CompositeDisposable compositeDisposable) {
        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(recipeViewModel,
                this,
                compositeDisposable);
        recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        smoothScroller = createSmoothScroller();
    }

    private void observeRecipe(int recipeId) {
        recipe = recipeViewModel.getRecipe(recipeId);
        recipe.observe(this, recipe -> {
            if (recipe != null) {
                setViewState(new RecipeViewState(recipe));
            }
        });
    }

    private RecyclerView.SmoothScroller createSmoothScroller() {
        return new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
        recipe.removeObservers(this);
    }

    @Override
    public IRecipeViewHolder findViewHolderAtPosition(int position) {
        return (IRecipeViewHolder) recipeRecyclerView.findViewHolderForAdapterPosition(position);
    }

    @Override
    public void scrollToPosition(int position) {
        smoothScroller.setTargetPosition(position);
        recipeRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    @Override
    public void attachFragment(int layout, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layout, fragment);
        ft.commit();
    }

    @Override
    public void detachFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    public void setViewState(RecipeViewState viewState) {
        recipeRecyclerViewAdapter.setState(viewState);
    }
}
