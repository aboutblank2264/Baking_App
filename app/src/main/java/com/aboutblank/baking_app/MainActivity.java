package com.aboutblank.baking_app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.adapters.MainRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;

    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeRecyclerView();

        initializeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.onCleared();
    }

    /**
     * Initializes the Recipe list.
     * Initial list of recipes is set to empty until full list can be retrieved.
     */
    private void initializeRecyclerView() {
        adapter = new MainRecyclerViewAdapter(new ArrayList<MinimalRecipe>(), this);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mainRecyclerView.setAdapter(adapter);
    }

    private void initializeData() {
        mainViewModel = ((BakingApplication) getApplication()).getMainViewModel();

        //TODO: TESTING
        mainViewModel.clearLocalDatabase();

        mainViewModel.update();

        LiveData<List<MinimalRecipe>> minimalRecipes = mainViewModel.getMinimalRecipes();

        minimalRecipes.observe(this, new Observer<List<MinimalRecipe>>() {
            @Override
            public void onChanged(@Nullable List<MinimalRecipe> minimalRecipes) {
                adapter.updateRecipeList(minimalRecipes);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        int actualId = position + 1;
        Log.d(LOG_TAG, String.format("Position %d clicked, sending recipe %d", position, actualId));

        Intent launchRecipeIntent = new Intent(this, RecipeActivity.class);
        launchRecipeIntent.putExtra(getString(R.string.intent_recipe_id), actualId);

        startActivity(launchRecipeIntent);
    }
}
