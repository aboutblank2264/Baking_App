package com.aboutblank.baking_app.states;

import com.aboutblank.baking_app.data.model.Ingredient;

import java.util.List;
import java.util.Set;

public class IngredientViewState extends ViewState {
    private int recipeId;
    private List<Ingredient> ingredients;
    private Set<Integer> indexedIngredients;

    public IngredientViewState(int recipeId, List<Ingredient> ingredients, Set<Integer> indexedIngredients) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.indexedIngredients = indexedIngredients;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Integer> getIndexedIngredients() {
        return indexedIngredients;
    }

    public void setIndexedIngredients(Set<Integer> indexedIngredients) {
        this.indexedIngredients = indexedIngredients;
    }

    @Override
    public String toString() {
        return "IngredientViewState{" +
                "recipeId=" + recipeId +
                ", ingredients=" + ingredients +
                ", indexedIngredients=" + indexedIngredients +
                ", expanded=" + getState() +
                '}';
    }
}