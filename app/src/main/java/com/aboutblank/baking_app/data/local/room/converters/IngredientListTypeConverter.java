package com.aboutblank.baking_app.data.local.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.aboutblank.baking_app.data.model.Ingredient;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientListTypeConverter extends AbstractTypeConverter {
    private final static Type type = new TypeToken<List<Ingredient>>() {}.getType();

    @TypeConverter
    public static List<Ingredient> stringToList(String json) {
        return getGson().fromJson(json, type);
    }

    @TypeConverter
    public static String ListToString(List<Ingredient> list) {
        return getGson().toJson(list);
    }
}
