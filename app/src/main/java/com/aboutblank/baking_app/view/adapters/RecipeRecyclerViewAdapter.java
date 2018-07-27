package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder>
        implements ItemClickedListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private RecipeViewModel recipeViewModel;
    private CompositeDisposable compositeDisposable;
    private ItemClickedListener itemClickedListener;

    private RecipeViewState recipeViewState;

    public RecipeRecyclerViewAdapter(RecipeViewModel recipeViewModel,
                                     ItemClickedListener itemClickedListener,
                                     CompositeDisposable compositeDisposable) {
        this.recipeViewModel = recipeViewModel;
        this.compositeDisposable = compositeDisposable;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "Creating view holder with view type: " + viewType);
        switch (viewType) {
            case INGREDIENTS: {
                return getIngredientsViewHolder(parent);
            }
            default: {
                return getStepViewHolder(parent);
            }
        }
    }

    private CustomViewHolder getIngredientsViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);

        return new IngredientsViewHolder(view, this);
    }

    private CustomViewHolder getStepViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, int position) {
        if (recipeViewState != null) {
            Recipe recipe = recipeViewState.getRecipe();
            ViewState viewState;
            //If position is not the INGREDIENTS index
            if (position != INGREDIENTS) {
                // otherwise is a Step, reduce position by 1 to keep inline with steps
                int tempPosition = getRealStepPosition(position);
                viewState = new DetailViewState(recipe.getSteps().get(tempPosition));
                holder.setViewState(viewState);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(recipeViewState != null) {
            return recipeViewState.getNumberOfSteps();
        }
        return 0;
    }

    private final static int INGREDIENTS = 0;
    private final static int STEPS = 1;

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case INGREDIENTS:
                return INGREDIENTS;
            default:
                return STEPS;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //pass the item click up to the parent
        itemClickedListener.onItemClick(view, position);
    }

    public void setState(RecipeViewState recipeViewState) {
        this.recipeViewState = recipeViewState;
//        observeIndexedIngredients();

        notifyDataSetChanged();
    }

//    private void observeIndexedIngredients() {
//        Disposable disposable = recipeViewModel.getIndexedIngredients(recipeViewState.getRecipe().getId())
//                .subscribe(indexedIngredients -> {
//                    RecipeRecyclerViewAdapter.this.recipeViewState.setIndexedIngredients(indexedIngredients);
//                });
//
//        compositeDisposable.add(disposable);
//    }

    private int getRealStepPosition(int position) {
        return position > 0 ? position - 1 : position;
    }
}
