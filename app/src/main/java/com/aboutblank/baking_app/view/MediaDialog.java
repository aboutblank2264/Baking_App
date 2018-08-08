package com.aboutblank.baking_app.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private MediaPlayerView otherMediaPlayerView;
    private ReturnPlayerListener returnPlayerListener;

    private boolean isShowing = false;

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

        Log.d("TESTING", "onCreateView");

        takePlayer(otherMediaPlayerView);
        isShowing = true;

        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;

        if (returnPlayerListener != null) {
            returnPlayerListener.onDismiss(otherMediaPlayerView);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("TESTING", "onCancel");
        dismiss();
    }

    public void setOtherMediaPlayerView(MediaPlayerView otherMediaPlayerView) {
        this.otherMediaPlayerView = otherMediaPlayerView;
    }

    public void setReturnPlayerListener(ReturnPlayerListener returnPlayerListener) {
        this.returnPlayerListener = returnPlayerListener;
    }

    private void takePlayer(MediaPlayerView otherPlayerView) {
        mediaPlayerView.switchToMediaPlayer(otherPlayerView);
    }

    public void returnPlayer(MediaPlayerView otherPlayerView) {
        otherPlayerView.switchToMediaPlayer(mediaPlayerView);
    }

    public boolean isShowing() {
        return isShowing;
    }

    public static class Builder {
        private Fragment parentFragment;
        private MediaPlayerView otherPlayerView;
        private ReturnPlayerListener listener;

        public Builder setParentFragment(Fragment parentFragment) {
            this.parentFragment = parentFragment;
            return this;
        }

        public Builder setOtherPlayerView(MediaPlayerView otherPlayerView) {
            this.otherPlayerView = otherPlayerView;
            return this;
        }

        public Builder setListener(ReturnPlayerListener listener) {
            this.listener = listener;
            return this;
        }

        public MediaDialog build() {
            MediaDialog mediaDialog = new MediaDialog();

            mediaDialog.setReturnPlayerListener(listener);

            if (parentFragment != null) {
                mediaDialog.setTargetFragment(parentFragment, 100);
            }
            if (otherPlayerView != null) {
                mediaDialog.setOtherMediaPlayerView(otherPlayerView);
            }

            return mediaDialog;
        }
    }

    public interface ReturnPlayerListener {
        void onDismiss(MediaPlayerView mediaPlayerView);
    }
}
