package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.disposables.CompositeDisposable;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MainViewModel mainViewModel;

    private CompositeDisposable compositeDisposable;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    private IntroViewHolder getIntroViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_introduction, parent, false);
        return new IntroViewHolder(view, mainViewModel.getPlayer(), compositeDisposable);
    }

    private StepViewHolder getIngredientsViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients, parent, false);

        return null;
    }

    private RecyclerView.ViewHolder getStepViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);

        return new StepViewHolder(view, mainViewModel.getPlayer(), compositeDisposable);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((IRecipeViewHolder)holder).bindViewHolder();
    }

    @Override
    public int getItemCount() {
        return 0;
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

//    class StepViewHolder extends RecyclerView.ViewHolder {
//
//        public StepViewHolder(View itemView, @VIEW_TYPE int viewType) {
//            super(itemView);
//        }
//    }
//
    private final static int INTRODUCTION = 0;
    private final static int INGREDIENTS = 1;
    private final static int STEPS = 2;

    @IntDef({INTRODUCTION, INGREDIENTS, STEPS})
    @Retention(RetentionPolicy.SOURCE)
    private @interface VIEW_TYPE {
    }
}
