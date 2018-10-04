package com.aboutblank.baking_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.fragments.DetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements BaseActivity {

    private DetailFragment detailFragment;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(findViewById(R.id.detail_toolbar));

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        detailFragment.setArguments(getIntent().getExtras());

        if (getSupportActionBar() != null && getIntent().getExtras() != null) {
            getSupportActionBar().setTitle(getIntent().getExtras().getString(getString(R.string.toolbar_title),
                    getString(R.string.app_name)));
        }
    }

    public RecipeViewModel getRecipeViewModel() {
        if (recipeViewModel == null) {
            recipeViewModel = ((BakingApplication) getApplication()).getRecipeViewModel();
        }
        return recipeViewModel;
    }

    @Override
    public void setState(ViewState viewState) {
        detailFragment.setViewState(viewState);
    }
}
