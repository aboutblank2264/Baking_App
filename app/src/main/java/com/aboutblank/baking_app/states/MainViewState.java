package com.aboutblank.baking_app.states;

import com.aboutblank.baking_app.data.model.MinimalRecipe;

import java.util.List;

public class MainViewState {
    private List<MinimalRecipe> minimalRecipes;

    public List<MinimalRecipe> getMinimalRecipes() {
        return minimalRecipes;
    }

    public void setMinimalRecipes(List<MinimalRecipe> minimalRecipes) {
        this.minimalRecipes = minimalRecipes;
    }
}
