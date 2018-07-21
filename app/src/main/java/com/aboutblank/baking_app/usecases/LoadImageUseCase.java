package com.aboutblank.baking_app.usecases;

import android.widget.ImageView;

import com.aboutblank.baking_app.utils.ImageUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoadImageUseCase {
    ImageUtils imageUtils;

    @Inject
    public LoadImageUseCase(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    public void loadImage(ImageView imageView, String imageUrl) {
        imageUtils.loadImage(imageView, imageUrl);
    }
}
