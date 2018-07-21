package com.aboutblank.baking_app.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.IRecipeHolderListener;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

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

    private IngredientViewState ingredientViewState;

    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;
    private IRecipeHolderListener recipeHolderListener;

    public IngredientsViewHolder(View view,
                                 RecipeViewModel recipeViewModel,
                                 IRecipeHolderListener recipeHolderListener,
                                 CompositeDisposable compositeDisposable) {
        super(view);
        this.recipeHolderListener = recipeHolderListener;
        ButterKnife.bind(this, view);

        this.recipeViewModel = recipeViewModel;
        this.compositeDisposable = compositeDisposable;

        expandableLayout.setOnExpansionUpdateListener(recipeHolderListener);

        createIngredientItemRecyclerViewAdapter();
        title.setOnClickListener(this);
    }

    private void createIngredientItemRecyclerViewAdapter() {
        ingredientItemRecyclerViewAdapter = new IngredientItemRecyclerViewAdapter(new ArrayList<>(), this);
        ingredientRecycler.setAdapter(ingredientItemRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    public void onClick(View view) {
        recipeHolderListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public boolean isExpanded() {
        return ingredientViewState.getState() == ViewState.EXTENDED;
    }

    @Override
    public ViewState getViewState() {
        return ingredientViewState;
    }

    @Override
    public void setViewState(ViewState viewState) {
        if(viewState.getClass() == IngredientViewState.class) {
            ingredientViewState = (IngredientViewState) viewState;

            ingredientItemRecyclerViewAdapter.update(ingredientViewState.getIngredients(),
                    ingredientViewState.getIndexedIngredients());
            expand();
        }
    }

    private void expand() {
        Log.d(LOG_TAG, "Expand: " + isExpanded());
        expandableLayout.setExpanded(isExpanded());
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(LOG_TAG, "Adding item position to indexed ingredients " + position);
        recipeViewModel.indexIngredient(ingredientViewState.getRecipeId(), position);
    }
}
