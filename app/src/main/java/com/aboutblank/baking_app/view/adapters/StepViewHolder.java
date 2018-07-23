package com.aboutblank.baking_app.view.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.IRecipeHolderListener;
import com.aboutblank.baking_app.view.ParentView;
import com.aboutblank.baking_app.view.fragments.DetailFragment;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class StepViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.step_view)
    CardView cardView;

    @BindView(R.id.step_short_description)
    TextView shortDescription;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.frame_holder)
    View holder;

    private DetailViewState detailViewState;
    private DetailFragment detailFragment;

    private IRecipeHolderListener recipeHolderListener;
    private ParentView parentView;
    private RecipeViewModel recipeViewModel;

    public StepViewHolder(View view,
                          RecipeViewModel recipeViewModel,
                          IRecipeHolderListener recipeHolderListener,
                          ParentView parentView,
                          CompositeDisposable compositeDisposable) {
        super(view);
        this.recipeHolderListener = recipeHolderListener;
        this.parentView = parentView;
        ButterKnife.bind(this, view);

        this.recipeViewModel = recipeViewModel;

        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        recipeHolderListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public boolean isExpanded() {
        return detailViewState.getState() == ViewState.EXTENDED;
    }

    @Override
    public ViewState getViewState() {
        return detailViewState;
    }

    @Override
    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == DetailViewState.class) {
            detailViewState = (DetailViewState) viewState;

            Log.d(LOG_TAG, "New view state: " + viewState.toString());

            if (detailViewState.hasShortDescription()) {
                shortDescription.setText(detailViewState.getShortDescription());
            }
            if (isExpanded()) {
                if (detailFragment == null) {
                    detailFragment = createDetailFragment();
                }
                detailFragment.setViewState(detailViewState);
            } else {
                if (detailFragment != null) {
                    parentView.detachFragment(detailFragment);
                }
            }
        }
    }

    private DetailFragment createDetailFragment() {
        detailFragment = new DetailFragment();

        int uniqueId = getUniqueId();
        holder.setId(uniqueId);
        parentView.attachFragment(uniqueId, detailFragment);
        detailFragment.setRecipeViewModel(recipeViewModel);

        return detailFragment;
    }

    private int getUniqueId() {
        return (int) System.currentTimeMillis();
    }
}