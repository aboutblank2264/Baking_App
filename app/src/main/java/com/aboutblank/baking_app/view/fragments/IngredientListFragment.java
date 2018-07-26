package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.DetailActivity;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.adapters.IngredientItemRecyclerViewAdapter;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IngredientListFragment extends BaseFragment implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private static final String INGREDIENTS = "ingredients";
    private static final String INDEXEDINGREDIENTS = "indexed";

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;

    private IngredientItemRecyclerViewAdapter ingredientItemRecyclerViewAdapter;

    private IngredientViewState ingredientViewState;
    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        onLoadInstanceState(savedInstanceState);

        DetailActivity detailActivity = (DetailActivity) Objects.requireNonNull(getActivity());
        recipeViewModel = detailActivity.getRecipeViewModel();
        compositeDisposable = detailActivity.getCompositeDisposable();
        setupRecyclerView();

        subscribeToIndexedIngredients();

        return view;
    }

    private void setupRecyclerView() {
        ingredientItemRecyclerViewAdapter = new IngredientItemRecyclerViewAdapter(new ArrayList<>(), this);
        ingredientRecycler.setAdapter(ingredientItemRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        loadViewAdapter();
    }

    private void onLoadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ingredientViewState = new IngredientViewState();
            ingredientViewState.setIngredients(savedInstanceState.getParcelableArrayList(INGREDIENTS));
            ingredientViewState.setIndexedIngredients(savedInstanceState.getIntegerArrayList(INDEXEDINGREDIENTS));

            Log.d(LOG_TAG, "Loading saved IngredientViewState: " + ingredientViewState.toString());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INGREDIENTS, (ArrayList<? extends Parcelable>) ingredientViewState.getIngredients());
        outState.putIntegerArrayList(INDEXEDINGREDIENTS, (ArrayList<Integer>) ingredientViewState.getIndexedIngredients());
    }

    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == IngredientViewState.class) {
            ingredientViewState = (IngredientViewState) viewState;
        }
        loadViewAdapter();
    }

    public void subscribeToIndexedIngredients() {
        Disposable disposable = recipeViewModel.getIndexedIngredients(ingredientViewState.getRecipeId())
                .subscribe(indexedIngredients ->
                        ingredientItemRecyclerViewAdapter.updateIndexedIngredients(indexedIngredients));
        compositeDisposable.add(disposable);
    }

    private void loadViewAdapter() {
        if (ingredientItemRecyclerViewAdapter != null) {
            ingredientItemRecyclerViewAdapter.update(ingredientViewState.getIngredients(),
                    ingredientViewState.getIndexedIngredients());
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(LOG_TAG, "Adding item position to indexed ingredients " + position);
        recipeViewModel.indexIngredient(ingredientViewState.getRecipeId(), position);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_ingredients_list;
    }
}
