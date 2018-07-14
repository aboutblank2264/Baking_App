package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class LoadMediaPlayerUseCase {
    private final MediaPlayerPool mediaPlayerPool;

    @Inject
    public LoadMediaPlayerUseCase(MediaPlayerPool mediaPlayerPool) {
        this.mediaPlayerPool = mediaPlayerPool;
    }

    public Single<MediaPlayer> getPlayer() {
        return Single.just(mediaPlayerPool.getPlayer());
    }
    
    public void clear() {
        mediaPlayerPool.cleanup();
    }
}
