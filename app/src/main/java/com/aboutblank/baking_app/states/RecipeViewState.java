package com.aboutblank.baking_app.states;

import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.model.Recipe;

import java.util.Set;

public class RecipeViewState extends ViewState {
    private Recipe recipe;
    private int numberOfSteps;
    private int currentPosition;
    private Set<Integer> indexedIngredients;

    public RecipeViewState() {
    }

    public RecipeViewState(@NonNull Recipe recipe) {
        this.recipe = recipe;
        // set the total number of items in RecyclerView including Ingredients
        this.numberOfSteps = recipe.getSteps().size() + 1;
    }

    public Recipe getRecipe() {
        return recipe;
    }
    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Set<Integer> getIndexedIngredients() {
        return indexedIngredients;
    }

    public void setIndexedIngredients(Set<Integer> indexedIngredients) {
        this.indexedIngredients = indexedIngredients;
    }
}
