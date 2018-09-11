package com.aboutblank.baking_app.player;

import android.content.Context;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MediaPlayerPool {
    private final String LOG_TAG = getClass().getSimpleName();

    private Context context;
    private TrackSelector trackSelector;
    private ExtractorMediaSource.Factory extractorMediaSource;

    private MediaPlayer mediaPlayer;

    @Inject
    public MediaPlayerPool(Context context, TrackSelector trackSelector,
                           ExtractorMediaSource.Factory extractorMediaSource) {
        this.context = context;
        this.trackSelector = trackSelector;
        this.extractorMediaSource = extractorMediaSource;
    }

    public MediaPlayer getPlayer(boolean samePlayer) {
        if (mediaPlayer != null) {
            Log.d(LOG_TAG, "Retrieving player, reset: " + samePlayer);
            if (!samePlayer) {
                mediaPlayer.getExoPlayer().stop(true);
            }
        } else {
            Log.d(LOG_TAG, "Creating new player");
            mediaPlayer = new MediaPlayer(ExoPlayerFactory.newSimpleInstance(context, trackSelector),
                    extractorMediaSource);
        }
        return mediaPlayer;
    }

    public MediaPlayer getPlayer() {
        return mediaPlayer;
    }

    // Release the mediaPlayer and set to null.
    // Must call this method at some point to make sure
    public void cleanup() {
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
