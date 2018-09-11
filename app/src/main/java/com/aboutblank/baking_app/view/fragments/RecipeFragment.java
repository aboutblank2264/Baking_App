package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.RecipeActivity;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.view.adapters.RecipeRecyclerViewAdapter;

import java.util.Objects;

import butterknife.BindView;

public class RecipeFragment extends BaseFragment implements ItemClickedListener {

    @BindView(R.id.recipe_recycler)
    RecyclerView recipeRecyclerView;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;

    private ItemClickedListener itemClickedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemClickedListener = (RecipeActivity) Objects.requireNonNull(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(this);
        recipeRecyclerView.setAdapter(recipeRecyclerViewAdapter);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_recipe;
    }

    @Override
    public void saveFragment(FragmentManager fragmentManager) {

    }

    @Override
    public void setViewState(ViewState viewState) {
        if(viewState.getClass() == RecipeViewState.class) {
            recipeRecyclerViewAdapter.setState((RecipeViewState) viewState);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //pass the item click up to the parent
        itemClickedListener.onItemClick(view, position);
    }
}
