package com.aboutblank.baking_app.view.adapters;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends CustomViewHolder implements View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.step_view)
    CardView cardView;

    @BindView(R.id.step_short_description)
    TextView shortDescription;

    private ItemClickedListener itemClickedListener;

    public StepViewHolder(View view, ItemClickedListener itemClickedListener) {
        super(view);
        ButterKnife.bind(this, view);
        this.itemClickedListener = itemClickedListener;

        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //pass the item click up to the parent
        itemClickedListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == DetailViewState.class) {
            DetailViewState detailViewState = (DetailViewState) viewState;

            if (detailViewState.hasShortDescription()) {
                shortDescription.setText(detailViewState.getShortDescription());
            }
        }
    }
}