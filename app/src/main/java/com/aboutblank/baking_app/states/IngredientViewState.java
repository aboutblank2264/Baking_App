package com.aboutblank.baking_app.states;

import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewState extends ViewState {
    private int recipeId;
    private List<Ingredient> ingredients;
    private List<Integer> indexedIngredients;

    public IngredientViewState(Builder builder) {
        this.recipeId = builder.recipeId;
        this.ingredients = builder.ingredients;
        this.indexedIngredients = builder.indexedIngredients;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Integer> getIndexedIngredients() {
        return indexedIngredients;
    }

    @Override
    public String toString() {
        return "IngredientViewState{" +
                "recipeId=" + recipeId +
                ", ingredients=" + ingredients +
                ", indexedIngredients=" + indexedIngredients +
                '}';
    }

    public static class Builder {
        private int recipeId;
        private List<Ingredient> ingredients;
        private List<Integer> indexedIngredients;

        public Builder(Recipe recipe) {
            recipeId = recipe.getId();
            ingredients = recipe.getIngredients();
        }

        public Builder(int recipeId) {
            this.recipeId = recipeId;
        }

        public Builder setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder setIndexedIngredients(List<Integer> indexedIngredients) {
            this.indexedIngredients = indexedIngredients;
            return this;
        }

        public IngredientViewState build() {
            if (indexedIngredients == null) {
                indexedIngredients = new ArrayList<>();
            }
            return new IngredientViewState(this);
        }
    }
}
