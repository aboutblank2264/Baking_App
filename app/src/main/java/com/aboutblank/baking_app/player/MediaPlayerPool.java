package com.aboutblank.baking_app.player;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

@Singleton
public class MediaPlayerPool {
    private static final int MAX_PLAYERS = 2;

    private Context context;
    private TrackSelector trackSelector;
    private ExtractorMediaSource.Factory extractorMediaSource;

    private Queue<MediaPlayer> mediaPlayers;

    @Inject
    public MediaPlayerPool(Context context, TrackSelector trackSelector,
                           ExtractorMediaSource.Factory extractorMediaSource) {
        this.context = context;
        this.trackSelector = trackSelector;
        this.extractorMediaSource = extractorMediaSource;

        mediaPlayers = new ArrayDeque<>();
    }

    public Single<MediaPlayer> getPlayer() {
        return Single.create(new SingleOnSubscribe<MediaPlayer>() {
            @Override
            public void subscribe(SingleEmitter<MediaPlayer> emitter) throws Exception {
                MediaPlayer mediaPlayer;
                if (mediaPlayers.size() < MAX_PLAYERS) {
                    //If pool is not maxed out, create a new ExoPlayer and add it to the queue
                    mediaPlayer = new MediaPlayer(ExoPlayerFactory.newSimpleInstance(context, trackSelector), extractorMediaSource);
                    mediaPlayers.add(mediaPlayer);
                } else {
                    //Else pop the head of the queue and add it to the back then return the mediaPlayer.
                    mediaPlayer = mediaPlayers.remove();
                    mediaPlayers.add(mediaPlayer);
                }
                emitter.onSuccess(mediaPlayer);
            }
        });
    }

    // Release all the mediaPlayers and clears the queue
    // Must call this method at some point to make sure
    public void cleanup() {
        while (!mediaPlayers.isEmpty()) {
            mediaPlayers.remove().release();
        }
    }
}
