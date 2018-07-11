package com.aboutblank.baking_app.usecases;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;
import com.aboutblank.baking_app.schedulers.ISchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class LoadMediaPlayerUseCase {
    private final MediaPlayerPool mediaPlayerPool;
    private final ISchedulerProvider schedulerProvider;

    @Inject
    public LoadMediaPlayerUseCase(MediaPlayerPool mediaPlayerPool, ISchedulerProvider schedulerProvider) {
        this.mediaPlayerPool = mediaPlayerPool;
        this.schedulerProvider = schedulerProvider;
    }

    public Single<MediaPlayer> getPlayer() {
        return Single.just(mediaPlayerPool.getPlayer()).subscribeOn(schedulerProvider.computation());
    }
    
    public void clear() {
        mediaPlayerPool.cleanup();
    }
}
