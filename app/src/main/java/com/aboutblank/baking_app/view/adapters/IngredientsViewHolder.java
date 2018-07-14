package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.ItemClickedListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IngredientsViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener, ItemClickedListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.ingredients_title)
    TextView title;

    @BindView(R.id.ingredient_expandable)
    ExpandableLayout expandableLayout;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;
    private IngredientItemRecyclerViewAdapter ingredientItemRecyclerViewAdapter;

    private int recipeId;

    private MainViewModel mainViewModel;
    private CompositeDisposable compositeDisposable;

    public IngredientsViewHolder(View view, MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;
        this.compositeDisposable = compositeDisposable;

        createIngredientItemRecyclerViewAdapter();
        title.setOnClickListener(this);
    }

    private void createIngredientItemRecyclerViewAdapter() {
        ingredientItemRecyclerViewAdapter = new IngredientItemRecyclerViewAdapter(new ArrayList<>(), this);
        ingredientRecycler.setAdapter(ingredientItemRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public void onClick(View v) {
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
        } else {
            expandableLayout.expand();
        }
    }

    @Override
    public void bindViewHolder(@NonNull final Recipe recipe, int position) {
        recipeId = recipe.getId();

        Disposable disposable = mainViewModel.getIndexedIngredients(recipeId)
                .subscribe(ingredientSet -> {
                    Log.d(LOG_TAG, "This is called");
                    ingredientItemRecyclerViewAdapter.update(recipe.getIngredients(), ingredientSet);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onItemClick(View view, int position) {
        mainViewModel.indexIngredient(recipeId, position);
    }
}
