package com.aboutblank.baking_app.usecases;

import android.support.annotation.Nullable;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerPool;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class LoadMediaPlayerUseCase {
    private final String LOG_TAG = getClass().getSimpleName();
    private final MediaPlayerPool mediaPlayerPool;

    //https://medium.com/@Cypressious/rxjava-kotlin-conditionally-delaying-the-first-item-in-a-stream-9d4e7a8d0071
//    private BehaviorSubject<Boolean> capturePlayer = BehaviorSubject.createDefault(false);

    private boolean capturePlayer;

    @Inject
    public LoadMediaPlayerUseCase(MediaPlayerPool mediaPlayerPool) {
        this.mediaPlayerPool = mediaPlayerPool;
    }

    @Nullable
    public MediaPlayer getMediaPlayer(boolean samePlayer) {
        MediaPlayer mediaPlayer = null;
        if (!capturePlayer) {
            mediaPlayer = mediaPlayerPool.getMediaPlayer(samePlayer);
        }
        return mediaPlayer;
    }

    public Single<MediaPlayer> getPlayer(boolean samePlayer) {
        Single<MediaPlayer> mediaPlayerSingle = mediaPlayerPool.getPlayer(samePlayer)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

//        return mediaPlayerSingle.delaySubscription(capturePlayer.filter(b -> !b));
        return mediaPlayerSingle;
    }

//    public Single<MediaPlayer> capturePlayer(boolean samePlayer) {
//        Single<MediaPlayer> mediaPlayerSingle = getPlayer(samePlayer);
////        capturePlayer.onNext(true);
//        return mediaPlayerSingle;
//    }

    public MediaPlayer capturePlayer(boolean samePlayer) {
        MediaPlayer mediaPlayer = getMediaPlayer(samePlayer);
        capturePlayer = true;

        return mediaPlayer;
    }

    public void releasePlayer() {
        capturePlayer = false;
    }

    public void clear() {
        mediaPlayerPool.cleanup();
    }
}
