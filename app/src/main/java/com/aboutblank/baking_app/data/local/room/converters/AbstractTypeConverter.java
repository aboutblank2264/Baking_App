package com.aboutblank.baking_app.data.local.room.converters;

import com.google.gson.Gson;

public abstract class AbstractTypeConverter {
    private static Gson gson;

    public static Gson getGson() {
        if(gson == null) {
            gson = new Gson();
        }

        return gson;
    }
}
