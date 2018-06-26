package com.aboutblank.baking_app.data.local.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.aboutblank.baking_app.data.model.MinimalRecipe;
import com.aboutblank.baking_app.data.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT id, name FROM recipes")
    LiveData<List<MinimalRecipe>> getMinimalRecipes();

    @Query("SELECT * FROM recipes WHERE id = :id")
    LiveData<Recipe> getRecipe(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe... recipes);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Recipe... recipes);

    @Delete
    void delete(Recipe... recipes);

    @Query("DELETE FROM recipes")
    void deleteAll();
}
