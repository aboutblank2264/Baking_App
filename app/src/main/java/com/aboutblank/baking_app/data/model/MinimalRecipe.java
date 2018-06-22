package com.aboutblank.baking_app.data.model;

public class MinimalRecipe {
    private int id;
    private String name;

    public MinimalRecipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
