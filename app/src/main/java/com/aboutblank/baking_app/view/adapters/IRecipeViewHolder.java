package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.model.Recipe;

public interface IRecipeViewHolder {
    void bindViewHolder(@NonNull Recipe recipe, int positions);
}
