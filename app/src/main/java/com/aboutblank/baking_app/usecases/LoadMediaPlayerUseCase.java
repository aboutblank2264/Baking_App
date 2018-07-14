package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class LoadMediaPlayerUseCase {
    private final MediaPlayerPool mediaPlayerPool;

    @Inject
    public LoadMediaPlayerUseCase(MediaPlayerPool mediaPlayerPool) {
        this.mediaPlayerPool = mediaPlayerPool;
    }

    public Single<MediaPlayer> getPlayer() {
        return mediaPlayerPool.getPlayer()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public void clear() {
        mediaPlayerPool.cleanup();
    }
}
