package com.aboutblank.baking_app.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageUtils {
    private static final String LOG_TAG = ImageUtils.class.getSimpleName();
    private static WeakReference<Context> context;

    @Inject
    public ImageUtils(Context context) {
        ImageUtils.context = new WeakReference<>(context);
    }

    public static void loadImage(@NonNull ImageView imageView, @NonNull String url) {
        if(context.get() != null) {
            Glide.with(context.get())
                    .load(url)
                    .into(imageView);
        } else {
            Log.e(LOG_TAG, "Uh oh! Reference to Context was null! Unable to load image");
        }
    }
}
