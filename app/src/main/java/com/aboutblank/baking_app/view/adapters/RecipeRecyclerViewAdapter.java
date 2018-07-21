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
import com.aboutblank.baking_app.states.IngredientViewState;
import com.aboutblank.baking_app.states.RecipeViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.IRecipeHolderListener;
import com.aboutblank.baking_app.view.ParentView;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IRecipeHolderListener {
    private final String LOG_TAG = getClass().getSimpleName();

    private RecipeViewModel recipeViewModel;
    private ParentView parentView;
    private CompositeDisposable compositeDisposable;

    private RecipeViewState recipeViewState;

    public RecipeRecyclerViewAdapter(RecipeViewModel recipeViewModel,
                                     ParentView parentView,
                                     CompositeDisposable compositeDisposable) {
        this.recipeViewModel = recipeViewModel;
        this.parentView = parentView;
        this.compositeDisposable = compositeDisposable;
        recipeViewState = new RecipeViewState();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

    private RecyclerView.ViewHolder getIngredientsViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);

        return new IngredientsViewHolder(view, recipeViewModel, this, compositeDisposable);
    }

    private RecyclerView.ViewHolder getStepViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view, recipeViewModel, this, parentView, compositeDisposable);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Log.d(LOG_TAG, "BindViewHolder called at position " + position);
        Log.d(LOG_TAG, "Class of ViewHolder: " + holder.getClass());
        Log.d(LOG_TAG, "Position: " + position);

        Recipe recipe = recipeViewState.getRecipe();
        ViewState viewState;
        //If position is past index 0 then it is Ingredients
        if (position == INGREDIENTS) {
            viewState = new IngredientViewState(recipe.getId(),
                    recipe.getIngredients(),
                    recipeViewState.getIndexedIngredients());
        } else {
            // otherwise is a Step, reduce position by 1 to keep inline with steps
            int tempPosition = getRealStepPosition(position);
            viewState = new DetailViewState(recipe.getSteps().get(tempPosition), ViewState.COLLAPSED);
        }
        ((IRecipeViewHolder) holder).setViewState(viewState);
    }

    @Override
    public int getItemCount() {
        return recipeViewState.getNumberOfSteps();
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
        int currentPosition = recipeViewState.getCurrentPosition();
        Log.d("OnItemClick", String.format("Old position: %d, New position: %d", currentPosition, position));

        //if the clicked position is the ingredients view, toggle just that view and do nothing to any other view.
        if (position == INGREDIENTS) {
            controlIngredientView(parentView, position);
        } else {
            controlStepViews(parentView, recipeViewState, currentPosition, position);
        }
        recipeViewState.setCurrentPosition(position);
    }

    private void controlIngredientView(ParentView parentView, int position) {
        IRecipeViewHolder ingredientsViewHolder = parentView.findViewHolderAtPosition(position);
        ViewState viewState = ingredientsViewHolder.getViewState();
        toggleViewState(viewState, ingredientsViewHolder.isExpanded());

        ingredientsViewHolder.setViewState(viewState);
    }

    // if oldView is extended, collapse and extend new view
    // if oldView is collapsed, extend new view.
    // if oldView == newView, toggle.
    private void controlStepViews(ParentView parentView, RecipeViewState recipeViewState, int currentPosition, int newPosition) {
        boolean sameStep = currentPosition == newPosition;

        IRecipeViewHolder oldViewHolder = parentView.findViewHolderAtPosition(currentPosition);
        if (oldViewHolder != null) {
            ViewState viewState = oldViewHolder.getViewState();
            if (sameStep) {
                toggleViewState(viewState, oldViewHolder.isExpanded());
            } else {
                // true here because we want to always collapse it so always assume it is expanded
                toggleViewState(viewState, true);
            }
            oldViewHolder.setViewState(viewState);
        }
        if (!sameStep) {
            int truePosition = getRealStepPosition(newPosition);
            IRecipeViewHolder newViewHolder = parentView.findViewHolderAtPosition(newPosition);
            ViewState viewState = new DetailViewState(recipeViewState.getRecipe().getSteps().get(truePosition),
                    ViewState.EXTENDED);

            newViewHolder.setViewState(viewState);
        }
    }

    private int getRealStepPosition(int position) {
        return position > 0 ? position - 1 : position;
    }

    private void toggleViewState(ViewState viewState, boolean isExpanded) {
        if (isExpanded) {
            viewState.setState(ViewState.COLLAPSED);
        } else {
            viewState.setState(ViewState.EXTENDED);
        }
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        if (state == ExpandableLayout.State.EXPANDING) {
            Log.d("ExpandableLayout", "Scrolling to position: " + recipeViewState.getCurrentPosition());
            parentView.scrollToPosition(recipeViewState.getCurrentPosition());
        }
    }

    public void setState(RecipeViewState recipeViewState) {
        this.recipeViewState = new RecipeViewState(recipeViewState.getRecipe());
        observeIndexedIngredients();

        notifyDataSetChanged();
    }

    private void observeIndexedIngredients() {
        Disposable disposable = recipeViewModel.getIndexedIngredients(recipeViewState.getRecipe().getId())
                .subscribe(indexedIngredients -> {
                    RecipeRecyclerViewAdapter.this.recipeViewState.setIndexedIngredients(indexedIngredients);
                });

        compositeDisposable.add(disposable);
    }
}
