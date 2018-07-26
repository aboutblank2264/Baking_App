package com.aboutblank.baking_app.states;

import android.support.annotation.NonNull;

import com.aboutblank.baking_app.data.model.Ingredient;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewState extends ViewState {
    private int recipeId;
    private List<Ingredient> ingredients;
    private List<Integer> indexedIngredients;

    public IngredientViewState() {
    }

    public IngredientViewState(@NonNull Recipe recipe) {
        this(recipe, new ArrayList<>());
    }

    public IngredientViewState(@NonNull Recipe recipe, @NonNull List<Integer> indexedIngredients) {
        this.recipeId = recipe.getId();
        this.ingredients = recipe.getIngredients();
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

    public List<Integer> getIndexedIngredients() {
        return indexedIngredients;
    }

    public void setIndexedIngredients(List<Integer> indexedIngredients) {
        this.indexedIngredients = indexedIngredients;
    }

    @Override
    public String toString() {
        return "IngredientViewState{" +
                "recipeId=" + recipeId +
                ", ingredients=" + ingredients +
                ", indexedIngredients=" + indexedIngredients +
                '}';
    }
}
