package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.DetailActivity;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.player.MediaPlayerView;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.states.ViewState;
import com.aboutblank.baking_app.view.MediaDialog;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class StepDetailFragment extends BaseFragment {
    private final String LOG_TAG = getClass().getSimpleName();
    private static final String DESCRIPTION = "description";
    private static final String VIDEO = "video";
    private static final String THUMBNAIL = "thumbnail";
    private static final String POSITION = "position";
    private static final String SAME_PLAYER = "same_player";

    public static final String STEP_DETAIL_FRAGMENT_TAG = "StepDetailFragment";

    @BindView(R.id.detail_description)
    TextView description;

    @BindView(R.id.detail_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.detail_player)
    MediaPlayerView playerView;

    private RecipeViewModel recipeViewModel;
    private DetailViewState detailViewState;
    private CompositeDisposable compositeDisposable;

    private boolean samePlayer = false;
    private boolean isFullScreen = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        onLoadInstanceState(savedInstanceState);

        DetailActivity detailActivity = (DetailActivity) Objects.requireNonNull(getActivity());
        recipeViewModel = detailActivity.getRecipeViewModel();
        compositeDisposable = detailActivity.getCompositeDisposable();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        playerView.setPlayWhenReady(false);
    }

    private void onLoadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            detailViewState = new DetailViewState.Builder()
                    .setDescription(savedInstanceState.getString(DESCRIPTION))
                    .setVideoUrl(savedInstanceState.getString(VIDEO))
                    .setThumbnailUrl(savedInstanceState.getString(THUMBNAIL))
                    .setCurrentPlaybackPosition(savedInstanceState.getLong(POSITION))
                    .build();
            samePlayer = savedInstanceState.getBoolean(SAME_PLAYER);

            Log.d(LOG_TAG, "Loading saved DetailViewState: " + detailViewState.toString());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "Saving state");
        outState.putAll(getBundle());
    }

    private MediaDialog fullscreenDialog;

    private void createFullscreenDialog() {
        if (fullscreenDialog == null) {
//            fullscreenDialog = new MediaDialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//                @Override
//                public void onBackPressed() {
//                    resetPlayer();
//                    super.onBackPressed();
//                }
//            };

        }
    }

    private void fullscreenPlayer() {
        Log.d(LOG_TAG, "Setting player to fullscreen");

        createFullscreenDialog();
        fullscreenDialog.takePlayer(playerView);
//        fullscreenDialog.fullscreen();

        isFullScreen = true;
    }

    private void resetPlayer() {
        Log.d(LOG_TAG, "Setting player to normal");

        if(isFullScreen) {
            fullscreenDialog.returnPlayer(playerView);
            fullscreenDialog.dismiss();

            isFullScreen = false;
        }
    }

    private void setDescriptionView(String descriptionString, boolean hasDescription) {
        if (hasDescription) {
            description.setText(descriptionString);
        } else {
            description.setVisibility(View.GONE);
        }
    }

    private void setVideoView(String videoUrl, boolean hasVideo) {
        if (hasVideo) {
            Disposable disposable = recipeViewModel.getPlayer(samePlayer).subscribe(player -> {
                if (!samePlayer) {
                    Log.d(LOG_TAG, "Preparing player with: " + videoUrl);
                    player.prepare(videoUrl);
                }
                player.seekToPosition(detailViewState.getCurrentPlaybackPosition());
                playerView.setPlayer(player);
                playerView.setPlayWhenReady(true);
            });
            compositeDisposable.add(disposable);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void setThumbnailView(String imageUrl, boolean hasThumbnail) {
        if (hasThumbnail) {
            Log.d(LOG_TAG, "Preparing thumbnail with: " + imageUrl);
            recipeViewModel.loadImage(thumbnail, imageUrl);
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }

    @Override
    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == DetailViewState.class) {
            detailViewState = (DetailViewState) viewState;
        }
        updateViews();
    }

    public MediaPlayerView getPlayerView() {
        return playerView;
    }

    private void updateViews() {
        //just to test, if description is null, most likely the fragment isn't finished creating
        if (description != null && detailViewState != null) {
            setDescriptionView(detailViewState.getDescription(), detailViewState.hasDescription());
            setThumbnailView(detailViewState.getThumbnailUrl(), detailViewState.hasThumbnail());
            setVideoView(detailViewState.getVideoUrl(), detailViewState.hasVideoUrl());

        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_step_detail;
    }

    @Override
    public void saveFragment(FragmentManager fragmentManager) {
        fragmentManager.putFragment(getBundle(), STEP_DETAIL_FRAGMENT_TAG, this);
    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(DESCRIPTION, detailViewState.getDescription());
        bundle.putString(VIDEO, detailViewState.getVideoUrl());
        bundle.putString(THUMBNAIL, detailViewState.getThumbnailUrl());
        bundle.putBoolean(SAME_PLAYER, true);

        if (playerView.getPlayer() != null) {
            Log.e("TIME", String.valueOf(playerView.getPlayer().getCurrentPosition()));
            bundle.putLong(POSITION, playerView.getPlayer().getCurrentPosition());
        }

        return bundle;
    }
}
