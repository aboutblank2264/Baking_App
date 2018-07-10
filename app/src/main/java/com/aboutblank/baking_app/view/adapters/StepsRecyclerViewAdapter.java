package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepViewHolder> {
    private final String LOG_TAG = getClass().getSimpleName();

    private List<Step> steps;
    private ItemClickedListener itemClickedListener;

    private MainViewModel mainViewModel;

    private CompositeDisposable compositeDisposable;

    public StepsRecyclerViewAdapter(MainViewModel mainViewModel, List<Step> steps,
                                    CompositeDisposable compositeDisposable, ItemClickedListener itemClickedListener) {
        this.mainViewModel = mainViewModel;
        this.steps = steps;
        this.compositeDisposable = compositeDisposable;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);

        return new StepViewHolder(view, mainViewModel.getPlayer(), compositeDisposable);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.setStep(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void update(List<Step> newSteps) {
        steps.clear();
        steps.addAll(newSteps);

        notifyDataSetChanged();
    }
}
