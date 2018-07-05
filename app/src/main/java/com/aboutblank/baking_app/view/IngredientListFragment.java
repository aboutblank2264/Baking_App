package com.aboutblank.baking_app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.adapters.IngredientsRecyclerViewAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class IngredientListFragment extends BaseFragment implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private Recipe recipe;

    @BindView(R.id.ingredients_title)
    TextView title;

    @BindView(R.id.ingredient_expandable)
    ExpandableLayout expandableLayout;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;
    IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ingredientsRecyclerViewAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredient>(), this);
        ingredientRecycler.setAdapter(ingredientsRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        mainViewModel = ((BakingApplication) requireActivity().getApplication()).getMainViewModel();

        return view;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        ingredientsRecyclerViewAdapter.update(recipe.getIngredients(),
                mainViewModel.getIndexedIngredients(recipe.getId()));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_ingredient_list;
    }

    @Override
    public void onItemClick(View view, int position) {
        mainViewModel.indexIngredient(recipe.getId(), position);
    }

    @OnClick(R.id.ingredients_title)
    public void onClick() {
        Log.d(LOG_TAG, "Title is clicked: " + expandableLayout.isExpanded());

        if(expandableLayout.isExpanded()) {
            expandableLayout.collapse();
        } else {
            expandableLayout.expand();
        }
    }
}
