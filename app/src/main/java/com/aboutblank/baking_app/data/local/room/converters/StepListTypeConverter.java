package com.aboutblank.baking_app.data.local.room.converters;

import android.arch.persistence.room.TypeConverter;

import com.aboutblank.baking_app.data.model.Step;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StepListTypeConverter extends AbstractTypeConverter {

    private final static Type type = new TypeToken<List<Step>>() {}.getType();

    @TypeConverter
    public static List<Step> stringToList(String json) {
        return getGson().fromJson(json, type);
    }

    @TypeConverter
    public static String ListToString(List<Step> list) {
        return getGson().toJson(list);
    }
}
