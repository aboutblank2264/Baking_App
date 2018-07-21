package com.aboutblank.baking_app.view.adapters;

import com.aboutblank.baking_app.states.ViewState;

public interface IRecipeViewHolder {
//    void bindViewHolder(@NonNull Recipe recipe, int positions);
//    void expand(boolean expand);
    boolean isExpanded();
    ViewState getViewState();
    void setViewState(ViewState viewState);
}
