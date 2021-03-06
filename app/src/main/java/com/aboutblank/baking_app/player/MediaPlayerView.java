package com.aboutblank.baking_app.player;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.exoplayer2.ui.PlayerView;

public class MediaPlayerView extends PlayerView {
    public MediaPlayerView(Context context) {
        super(context);
    }

    public MediaPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPlayer(MediaPlayer player) {
        super.setPlayer(player.getExoPlayer());
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        if(getPlayer() != null) {
            getPlayer().setPlayWhenReady(playWhenReady);
        }
    }

    // Switch the player to this MediaPlayerView
    public void switchToMediaPlayer(MediaPlayerView oldPlayerView) {
        PlayerView.switchTargetView(oldPlayerView.getPlayer(), oldPlayerView, this);
    }
}
