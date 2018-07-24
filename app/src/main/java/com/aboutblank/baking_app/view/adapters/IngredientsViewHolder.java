package com.aboutblank.baking_app.view.adapters;

import android.view.View;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.ItemClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsViewHolder extends CustomViewHolder
        implements View.OnClickListener {

    @BindView(R.id.item_ingredient_view)
    View layout;

    private ItemClickedListener itemClickedListener;

    public IngredientsViewHolder(View view, ItemClickedListener itemClickedListener) {
        super(view);
        ButterKnife.bind(this, view);
        this.itemClickedListener = itemClickedListener;

        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickedListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    void setViewState(ViewState viewState) {
        //Do nothing.
    }
}
