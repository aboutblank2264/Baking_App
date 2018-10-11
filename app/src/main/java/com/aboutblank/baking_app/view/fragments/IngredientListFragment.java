package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.adapters.IngredientItemRecyclerViewAdapter;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class IngredientListFragment extends BaseFragment implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();
    private static final String ID = "id";
    private static final String INGREDIENTS = "ingredients";
    private static final String INDEXED_INGREDIENTS = "indexed";

    public static final String INGREDIENT_LIST_FRAGMENT_TAG = "IngredientListFragment";

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;

    private IngredientItemRecyclerViewAdapter ingredientItemRecyclerViewAdapter;

    private IngredientViewState ingredientViewState;
    private RecipeViewModel recipeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        onLoadInstanceState(savedInstanceState);

        setupRecyclerView();

        subscribeToIndexedIngredients();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateViewAdapter(ingredientViewState);
    }

    private RecipeViewModel getRecipeViewModel() {
        if(recipeViewModel == null) {
            recipeViewModel = ((BakingApplication) requireActivity().getApplication()).getRecipeViewModel();
        }
        return recipeViewModel;
    }

    private void setupRecyclerView() {
        ingredientItemRecyclerViewAdapter = new IngredientItemRecyclerViewAdapter(new ArrayList<>(), this);
        ingredientRecycler.setAdapter(ingredientItemRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        ingredientRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
    }

    private void onLoadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ingredientViewState = new IngredientViewState.Builder(savedInstanceState.getInt(ID))
                    .setIngredients(savedInstanceState.getParcelableArrayList(INGREDIENTS))
                    .setIndexedIngredients(savedInstanceState.getIntegerArrayList(INDEXED_INGREDIENTS))
                    .build();

            Log.d(LOG_TAG, "Loading saved IngredientViewState: " + ingredientViewState.toString());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putAll(getBundle());
    }

    @Override
    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == IngredientViewState.class) {
            ingredientViewState = (IngredientViewState) viewState;
        }
        updateViewAdapter(ingredientViewState);
    }

    public void subscribeToIndexedIngredients() {
        List<Integer> indexedIngredients = getRecipeViewModel().getIndexedIngredients(ingredientViewState.getRecipeId());
        setViewState(new IngredientViewState.Builder(ingredientViewState.getRecipeId())
                .setIngredients(ingredientViewState.getIngredients())
                .setIndexedIngredients(indexedIngredients)
                .build());
    }

    private void updateViewAdapter(IngredientViewState viewState) {
        if (ingredientItemRecyclerViewAdapter != null) {
            ingredientItemRecyclerViewAdapter.update(viewState);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(LOG_TAG, "Adding item position to indexed ingredients " + position);
        getRecipeViewModel().indexIngredient(ingredientViewState.getRecipeId(), position);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_ingredients_list;
    }

    @Override
    public void saveFragment(FragmentManager fragmentManager) {
        fragmentManager.putFragment(getBundle(), INGREDIENT_LIST_FRAGMENT_TAG, this);
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, ingredientViewState.getRecipeId());
        bundle.putParcelableArrayList(INGREDIENTS, (ArrayList<? extends Parcelable>) ingredientViewState.getIngredients());
        bundle.putIntegerArrayList(INDEXED_INGREDIENTS, (ArrayList<Integer>) ingredientViewState.getIndexedIngredients());

        return bundle;
    }
}
