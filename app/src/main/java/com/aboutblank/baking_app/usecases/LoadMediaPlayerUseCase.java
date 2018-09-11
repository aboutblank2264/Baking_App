package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoadMediaPlayerUseCase {
    private final MediaPlayerPool mediaPlayerPool;

    @Inject
    public LoadMediaPlayerUseCase(MediaPlayerPool mediaPlayerPool) {
        this.mediaPlayerPool = mediaPlayerPool;
    }

    public MediaPlayer getPlayer(boolean samePlayer) {
        return mediaPlayerPool.getPlayer(samePlayer);
    }

    public void clear() {
        mediaPlayerPool.cleanup();
    }
}
