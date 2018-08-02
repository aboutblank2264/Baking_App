package com.aboutblank.baking_app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import com.aboutblank.baking_app.player.MediaPlayerView;

public class MediaDialog extends Dialog {
    private MediaPlayerView mediaPlayerView;

    public MediaDialog(Context context, int theme) {
        super(context, theme);
        mediaPlayerView = new MediaPlayerView(context);
    }

    public void takePlayer(MediaPlayerView otherPlayerView) {
        mediaPlayerView.switchToMediaPlayer(otherPlayerView);

        addContentView(mediaPlayerView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void returnPlayer(MediaPlayerView otherPlayerView) {
        otherPlayerView.switchToMediaPlayer(mediaPlayerView);
    }
}
