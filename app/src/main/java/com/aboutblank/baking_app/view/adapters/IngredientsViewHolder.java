package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.ItemClickedListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class IngredientsViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener, ItemClickedListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.ingredients_title)
    TextView title;

    @BindView(R.id.ingredient_expandable)
    ExpandableLayout expandableLayout;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    private int recipeId;

    private MainViewModel mainViewModel;
    private CompositeDisposable compositeDisposable;

    public IngredientsViewHolder(View view, MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        super(view);
        this.compositeDisposable = compositeDisposable;

        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;

        loadIngredientsRecyclerViewAdapter();

        title.setOnClickListener(this);
    }

    private void loadIngredientsRecyclerViewAdapter() {
        ingredientsRecyclerViewAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredient>(), this);
        ingredientRecycler.setAdapter(ingredientsRecyclerViewAdapter);
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
    public void onExpansionUpdate(float expansionFraction, int state) {

    }

    @Override
    public void bindViewHolder(@NonNull final Recipe recipe, int position) {
        recipeId = recipe.getId();

        Disposable disposable = mainViewModel.getIndexedIngredients(recipeId)
                .subscribeWith(new DisposableObserver<Set<Integer>>() {
                    @Override
                    public void onNext(Set<Integer> integers) {
                        Log.d(LOG_TAG, "This is called");

                        ingredientsRecyclerViewAdapter.update(recipe.getIngredients(), integers);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onItemClick(View view, int position) {
        mainViewModel.indexIngredient(recipeId, position);
    }
}