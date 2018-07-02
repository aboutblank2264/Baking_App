package com.aboutblank.baking_app.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageUtils {
    private static final String LOG_TAG = ImageUtils.class.getSimpleName();
    private Context context;

    @Inject
    public ImageUtils(Context context) {
        this.context = context;
    }

    public void loadImage(@NonNull ImageView imageView, @NonNull String url) {
        if(context != null) {
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        } else {
            Log.e(LOG_TAG, "Uh oh! Reference to Context was null! Unable to load image");
        }
    }
}
