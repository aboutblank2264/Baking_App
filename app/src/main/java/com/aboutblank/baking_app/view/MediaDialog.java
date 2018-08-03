package com.aboutblank.baking_app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.player.MediaPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaDialog extends DialogFragment {
    public static final String TAG = "MediaDialog";

    @BindView(R.id.media_player_view)
    MediaPlayerView mediaPlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_player, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void takePlayer(MediaPlayerView otherPlayerView) {
        mediaPlayerView.switchToMediaPlayer(otherPlayerView);
    }

    public void returnPlayer(MediaPlayerView otherPlayerView) {
        otherPlayerView.switchToMediaPlayer(mediaPlayerView);
    }
}
