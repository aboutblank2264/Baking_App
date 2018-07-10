package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.ItemClickedListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener, ItemClickedListener {

    @BindView(R.id.ingredients_title)
    TextView title;

    @BindView(R.id.ingredient_expandable)
    ExpandableLayout expandableLayout;

    @BindView(R.id.ingredient_recycler)
    RecyclerView ingredientRecycler;
    private IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter;

    public IngredientsViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);

        ingredientsRecyclerViewAdapter = new IngredientsRecyclerViewAdapter(new ArrayList<Ingredient>(), this);
        ingredientRecycler.setAdapter(ingredientsRecyclerViewAdapter);
        ingredientRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        title.setOnClickListener(this);
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
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        ingredientsRecyclerViewAdapter.update(recipe.getIngredients(), null);
    }

    @Override
    public void onItemClick(View view, int position) {
        //TODO create observable for clicked ingredients to push updates to.
    }
}
