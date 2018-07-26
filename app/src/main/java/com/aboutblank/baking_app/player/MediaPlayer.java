package com.aboutblank.baking_app.player;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;

//Wrapper for ExoPlayer
public class MediaPlayer {
    private ExtractorMediaSource.Factory mediaSourceFactory;
    private ExoPlayer exoPlayer;

    public MediaPlayer(@NonNull ExoPlayer exoPlayer, @NonNull ExtractorMediaSource.Factory mediaSourceFactory) {
        this.mediaSourceFactory = mediaSourceFactory;
        this.exoPlayer = exoPlayer;
    }

    public void prepare(@NonNull String videoUrl) {
        MediaSource mediaSource = mediaSourceFactory.createMediaSource(Uri.parse(videoUrl));

        exoPlayer.prepare(mediaSource);
    }

    public ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public void seekToPosition(long position) {
        exoPlayer.seekTo(position);
    }

    public void release() {
        exoPlayer.release();
    }
}
