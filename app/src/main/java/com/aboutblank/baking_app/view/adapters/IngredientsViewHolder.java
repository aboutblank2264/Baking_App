package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.IRecipeHolderListener;
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
    private boolean expanded = true;

    private MainViewModel mainViewModel;
    private CompositeDisposable compositeDisposable;

    public IngredientsViewHolder(View view,
                                 MainViewModel mainViewModel,
                                 IRecipeHolderListener recipeHolderListener,
                                 CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;
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
        expanded = !expandableLayout.isExpanded();
        expandableLayout.toggle();
    }

    @Override
    public void bindViewHolder(@NonNull final Recipe recipe, int position) {
        recipeId = recipe.getId();

        Disposable disposable = mainViewModel.getIndexedIngredients(recipeId)
                .subscribe(ingredientSet -> {
                    ingredientItemRecyclerViewAdapter.update(recipe.getIngredients(), ingredientSet);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void expand(boolean expand) {
        //Do nothing for now. Ingredient expand behavior is different from other views
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    @Override
    public void onItemClick(View view, int position) {
        mainViewModel.indexIngredient(recipeId, position);
    }
}
