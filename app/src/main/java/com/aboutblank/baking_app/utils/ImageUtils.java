package com.aboutblank.baking_app.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ImageUtils {
    private static final String LOG_TAG = ImageUtils.class.getSimpleName();

    @Inject
    public ImageUtils() {
    }

    public void loadImage(@NonNull ImageView imageView, @NonNull String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
