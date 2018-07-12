package com.aboutblank.baking_app.view.adapters;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    private MainViewModel mainViewModel;
    private CompositeDisposable compositeDisposable;

    private Recipe recipe;
    private int numberOfSteps;

    public RecipeRecyclerViewAdapter(MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        this.mainViewModel = mainViewModel;
        this.compositeDisposable = compositeDisposable;
    }

    // Create an observer to observe the LiveData recipe.
    // Returning the observer rather than being passed in a
    // LiveData lets caller provide LifeCycleOwner
    public Observer<Recipe> getObserver() {
        return new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null) {
                    // get the total number of items in RecyclerView including Ingredients
                    numberOfSteps = recipe.getSteps().size();
                    RecipeRecyclerViewAdapter.this.recipe = recipe;

                    notifyDataSetChanged();
                }
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "Creating view holder with view type: " + viewType);
        switch (viewType) {
            case INTRODUCTION: {
                return getIntroViewHolder(parent);
            }
            case INGREDIENTS: {
                return getIngredientsViewHolder(parent);
            }
            default: {
                return getStepViewHolder(parent);
            }
        }
    }

    private RecyclerView.ViewHolder getIntroViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduction, parent, false);
        return new IntroViewHolder(view, mainViewModel.getPlayer(), compositeDisposable);
    }

    private RecyclerView.ViewHolder getIngredientsViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientsViewHolder(view, mainViewModel, compositeDisposable);
    }

    private RecyclerView.ViewHolder getStepViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view, mainViewModel.getPlayer(), compositeDisposable);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Log.d(LOG_TAG, "BindViewHolder called at position " + position);
        Log.d(LOG_TAG, "Class of ViewHolder: " + holder.getClass());
        Log.d(LOG_TAG, "Position: " + position);
        ((IRecipeViewHolder) holder).bindViewHolder(recipe, position);
    }

    @Override
    public int getItemCount() {
        return numberOfSteps;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case INTRODUCTION:
                return INTRODUCTION;
            case INGREDIENTS:
                return INGREDIENTS;
            default:
                return STEPS;
        }
    }

    private final static int INTRODUCTION = 0;
    private final static int INGREDIENTS = 1;
    private final static int STEPS = 2;
}
