package com.aboutblank.baking_app.view;

import android.support.v4.app.Fragment;

import com.aboutblank.baking_app.view.adapters.IRecipeViewHolder;

public interface ParentView {
    IRecipeViewHolder findViewHolderAtPosition(int position);
    void scrollToPosition(int position);
    void attachFragment(int layout, Fragment fragment);
}
