package com.aboutblank.baking_app.view;

import android.util.Log;
import android.view.View;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerView;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

//Utility class to hold all the MediaPlayer methods for multiple views.
public class PlayerHandler {
    private MediaPlayerView playerView;
    private CompositeDisposable compositeDisposable;

    public PlayerHandler(MediaPlayerView playerView, CompositeDisposable compositeDisposable) {
        this.playerView = playerView;
        this.compositeDisposable = compositeDisposable;
    }

    public void pause() {
        if(playerView.getPlayer() != null) {
            playerView.getPlayer().setPlayWhenReady(false);
        }
    }

    // Subscribes the player and makes the player ready for playback.
    // returns a false if there is nothing to play.
    public boolean preparePlayer(Single<MediaPlayer> player, final String videoUrl) {
        boolean visible = true;
        if (videoUrl != null && !videoUrl.isEmpty()) {
            Log.d(PlayerHandler.class.getSimpleName(), "Loading player with url: " + videoUrl);
            subscribeToMedia(player, videoUrl);
        } else {
            playerView.setVisibility(View.GONE);
            visible = false;
        }
        return visible;
    }

    private void subscribeToMedia(Single<MediaPlayer> player, final String videoUrl) {
        compositeDisposable.add(player.subscribe(mediaPlayer -> {
            mediaPlayer.prepare(videoUrl);
            playerView.setPlayer(mediaPlayer);
        }));
    }
}
