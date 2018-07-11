package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.adapters.RecipeRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.recipe_recycler)
    RecyclerView recipeRecyclerView;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

    private MainViewModel mainViewModel;
    private LiveData<Recipe> recipe;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();

        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

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

    private void observeRecipe(int recipeId) {
        recipe = mainViewModel.getRecipe(recipeId);
        if (recipeRecyclerViewAdapter == null) {
            recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mainViewModel, compositeDisposable);
            recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }
        recipe.observe(this, recipeRecyclerViewAdapter.getObserver());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        recipe.removeObservers(this);
    }
}
