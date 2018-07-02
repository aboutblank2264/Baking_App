package com.aboutblank.baking_app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.view.adapters.IngredientsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListFragment extends BaseFragment {
    private List<Ingredient> ingredientList;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;
    IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        ingredientsRecyclerViewAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredient>());
        ingredientRecycler.setAdapter(ingredientsRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        return view;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
        ingredientsRecyclerViewAdapter.update(ingredientList);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_ingredient_list;
    }
}
