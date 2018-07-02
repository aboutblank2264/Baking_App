package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.view.ItemClickedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsViewHolder> {
    private List<Step> steps;
    private ItemClickedListener itemClickedListener;

    public StepsRecyclerViewAdapter(List<Step> steps, ItemClickedListener itemClickedListener) {
        this.steps = steps;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String shortDescription = steps.get(position).getShortDescription();

        holder.setShortDescription(shortDescription);
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

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_short_description)
        TextView shortDescription;

        StepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        void setShortDescription(@NonNull String description) {
            shortDescription.setText(description);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
