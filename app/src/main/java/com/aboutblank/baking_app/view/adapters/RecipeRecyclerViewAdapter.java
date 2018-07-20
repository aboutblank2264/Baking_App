package com.aboutblank.baking_app.view.adapters;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.view.IRecipeHolderListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IRecipeHolderListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable;

    private Recipe recipe;
    private int numberOfSteps;

    public RecipeRecyclerViewAdapter(MainViewModel mainViewModel,
                                     RecyclerView recyclerView,
                                     CompositeDisposable compositeDisposable) {
        this.mainViewModel = mainViewModel;
        this.recyclerView = recyclerView;
        this.compositeDisposable = compositeDisposable;
    }

    // Create an observer to observe the LiveData recipe.
    // Returning the observer rather than being passed in a
    // LiveData lets caller provide LifeCycleOwner
    public Observer<Recipe> getObserver() {
        return recipe -> {
            if (recipe != null) {
                // get the total number of items in RecyclerView including Ingredients
                numberOfSteps = recipe.getSteps().size() + 1;
                RecipeRecyclerViewAdapter.this.recipe = recipe;

                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "Creating view holder with view type: " + viewType);
        switch (viewType) {
//            case INTRODUCTION: {
//                return getIntroViewHolder(parent);
//            }
            case INGREDIENTS: {
                return getIngredientsViewHolder(parent);
            }
            default: {
                return getStepViewHolder(parent);
            }
        }
    }

    private RecyclerView.ViewHolder getIngredientsViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsViewHolder(view, mainViewModel, this, compositeDisposable);
    }

    private RecyclerView.ViewHolder getStepViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view, mainViewModel, this, compositeDisposable);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Log.d(LOG_TAG, "BindViewHolder called at position " + position);
        Log.d(LOG_TAG, "Class of ViewHolder: " + holder.getClass());
        Log.d(LOG_TAG, "Position: " + position);

        //If position is past index 0, Ingredients, reduce by 1 to keep inline with Steps
        int tempPosition = position > 0 ? position - 1 : position;
        ((IRecipeViewHolder) holder).bindViewHolder(recipe, tempPosition);
    }

    @Override
    public int getItemCount() {
        return numberOfSteps;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case INGREDIENTS:
                return INGREDIENTS;
            default:
                return STEPS;
        }
    }

    private final static int INGREDIENTS = 0;
    private final static int STEPS = 1;

    private int selectedPosition = 0;

    @Override
    public void onItemClick(View view, int position) {
        Log.d("OnItemClick", String.format("Old position: %d, New position: %d", selectedPosition, position));
        // if the new position is different
        if (position != selectedPosition) {
            IRecipeViewHolder oldViewHolder = (IRecipeViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedPosition);
            if (oldViewHolder != null && oldViewHolder.isExpanded()) {
                // hide the old view
                oldViewHolder.expand(false);
            }
        }
        // get the new clicked view
        IRecipeViewHolder newViewHolder = (IRecipeViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        // expand or retract the new view
        newViewHolder.expand(!newViewHolder.isExpanded());

        selectedPosition = position;
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        if (state == ExpandableLayout.State.EXPANDING) {
            Log.d("ExpandableLayout", "Scrolling to position: " + selectedPosition);
            recyclerView.smoothScrollToPosition(selectedPosition);
        }
    }
}
