package com.aboutblank.baking_app.usecases;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.view.MediaDialog;
import com.aboutblank.baking_app.view.fragments.StepDetailFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ShowMediaDialogUseCase {

    private MediaDialog mediaDialog;

    @Inject
    public ShowMediaDialogUseCase() {
    }

    public void show(FragmentManager fragmentManager, MediaPlayer mediaPlayer) {
        if(mediaPlayer != null) {
            mediaDialog = new MediaDialog();
        }
    }

    public void show(FragmentManager fragmentManager, StepDetailFragment stepDetailFragment) {
        if(stepDetailFragment.getPlayerView().getPlayer() != null) {
            if (mediaDialog == null) {
                mediaDialog = new MediaDialog();
            }
            mediaDialog.setTargetFragment(stepDetailFragment, 100);
            mediaDialog.takePlayer(stepDetailFragment.getPlayerView());

            FragmentTransaction ft = fragmentManager.beginTransaction();
            Fragment prev = fragmentManager.findFragmentByTag(MediaDialog.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            mediaDialog.show(ft, MediaDialog.TAG);
        }
    }

    public void dismiss(StepDetailFragment stepDetailFragment) {
        if(mediaDialog != null) {
            mediaDialog.returnPlayer(stepDetailFragment.getPlayerView());
            mediaDialog.dismiss();
        }
    }
}
