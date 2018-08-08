package com.aboutblank.baking_app.usecases;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aboutblank.baking_app.view.MediaDialog;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShowMediaDialogUseCase {

    private MediaDialog mediaDialog;
    private StepDetailFragment dialogOwner;

    private MediaDialog.ReturnPlayerListener listener;

    @Inject
    public ShowMediaDialogUseCase(LoadMediaPlayerUseCase loadMediaPlayerUseCase) {
        this.listener = mediaPlayer -> {
            loadMediaPlayerUseCase.releasePlayer();
            mediaDialog.returnPlayer(dialogOwner.getPlayerView());
        };
    }

    public void show(FragmentManager fragmentManager, StepDetailFragment stepDetailFragment) {
        if (stepDetailFragment != null) {
            mediaDialog = new MediaDialog.Builder()
                    .setListener(listener)
                    .setOtherPlayerView(stepDetailFragment.getPlayerView())
                    .setParentFragment(stepDetailFragment)
                    .build();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(MediaDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            mediaDialog.show(ft, MediaDialog.TAG);
        }
    }

    public void dismiss() {
        if (mediaDialog != null) {
            mediaDialog.dismiss();
        }
    }
}
