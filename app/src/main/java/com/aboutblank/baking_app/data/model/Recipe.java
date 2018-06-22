package com.aboutblank.baking_app.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey
    private int id;
    private String name;
    private int servings;
    private String image;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public Recipe(int id, String name, int servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
