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

//    IngredientListFragment ingredientListFragment;
//    RecipeFragment recipeFragment;

    @BindView(R.id.recipe_recycler)
    RecyclerView recipeRecyclerView;

    private MainViewModel mainViewModel;
    private LiveData<Recipe> recipe;
    private int recipeId = -1;

    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        compositeDisposable = new CompositeDisposable();

        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

        if (getIntent() != null) {
            recipeId = getIntent().getIntExtra(getString(R.string.intent_recipe_id), -1);

            Log.d(LOG_TAG, String.format("Id provided: %d", recipeId));

            recipe = mainViewModel.getRecipe(recipeId);

            RecipeRecyclerViewAdapter recipeRecyclerViewAdapter =
                    new RecipeRecyclerViewAdapter(recipeId, mainViewModel, compositeDisposable);
            recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);
            recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        } else {
            Log.e(LOG_TAG, "Launching intent must have a recipe id");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
