package com.aboutblank.baking_app.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aboutblank.baking_app.states.ViewState;

public abstract class CustomViewHolder extends RecyclerView.ViewHolder {
    public CustomViewHolder(View itemView) {
        super(itemView);
    }

    abstract void setViewState(ViewState viewState);
}
