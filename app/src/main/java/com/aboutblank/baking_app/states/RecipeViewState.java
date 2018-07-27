package com.aboutblank.baking_app.states;

import com.aboutblank.baking_app.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewState extends ViewState {
    private Recipe recipe;
    private int numberOfSteps;
    private int currentPosition;
    private List<Integer> indexedIngredients;

    public RecipeViewState(Builder builder) {
        this.recipe = builder.recipe;
        this.numberOfSteps = builder.numberOfSteps;
        this.currentPosition = builder.currentPosition;
        this.indexedIngredients = builder.indexedIngredients;
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

    public List<Integer> getIndexedIngredients() {
        return indexedIngredients;
    }

    public static class Builder {
        private Recipe recipe;
        private int numberOfSteps;
        private List<Integer> indexedIngredients;
        private int currentPosition;

        public Builder(Recipe recipe) {
            this.recipe = recipe;
            this.numberOfSteps = recipe.getSteps().size() + 1;
        }

        public Builder setIndexedIngredients(List<Integer> indexedIngredients) {
            this.indexedIngredients = indexedIngredients;
            return this;
        }

        public Builder setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
            return this;
        }

        public RecipeViewState build() {
            if(indexedIngredients == null) {
                indexedIngredients = new ArrayList<>();
            }
            return new RecipeViewState(this);
        }

    }
}
