package com.aboutblank.baking_app.utils;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExoPlayerUtils {
    private ExoPlayer exoPlayer;
    private ExtractorMediaSource.Factory extractorMediaSource;

    @Inject
    public ExoPlayerUtils(ExoPlayer exoPlayer, ExtractorMediaSource.Factory extractorMediaSource) {
        this.exoPlayer = exoPlayer;
        this.extractorMediaSource = extractorMediaSource;
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public ExtractorMediaSource.Factory getExtractorMediaSource() {
        return extractorMediaSource;
    }
}
