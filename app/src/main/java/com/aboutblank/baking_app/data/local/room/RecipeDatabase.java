package com.aboutblank.baking_app.data.local.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.aboutblank.baking_app.data.local.room.converters.IngredientListTypeConverter;
import com.aboutblank.baking_app.data.local.room.converters.StepListTypeConverter;
import com.aboutblank.baking_app.data.model.Recipe;

@Database(entities = Recipe.class, version = 1)
@TypeConverters({StepListTypeConverter.class, IngredientListTypeConverter.class})
public abstract class RecipeDatabase extends RoomDatabase {

    private static final String DB_NAME = "recipe_book.db";
    private static volatile RecipeDatabase instance;

    public static synchronized RecipeDatabase getInstance(Context context) {
        if(instance == null) {
            instance = create(context);
        }

        return instance;
    }

    private static RecipeDatabase create(Context context) {
        return Room.databaseBuilder(context, RecipeDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract RecipeDao getRecipeDao();

}
