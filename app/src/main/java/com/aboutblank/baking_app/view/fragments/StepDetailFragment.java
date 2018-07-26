package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import java.util.Objects;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class StepDetailFragment extends BaseFragment {
    private final String LOG_TAG = getClass().getSimpleName();
    private final static String DESCRIPTION = "description";
    private final static String VIDEO = "video";
    private final static String THUMBNAIL = "thumbnail";
    private final static String POSITION = "position";
    private final static String SAMEPLAYER = "same_player";

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
            detailViewState = new DetailViewState();
            detailViewState.setDescription(savedInstanceState.getString(DESCRIPTION));
            detailViewState.setVideoUrl(savedInstanceState.getString(VIDEO));
            detailViewState.setThumbnailUrl(savedInstanceState.getString(THUMBNAIL));
            detailViewState.setCurrentPlaybackPosition(savedInstanceState.getLong(POSITION));
            samePlayer = savedInstanceState.getBoolean(SAMEPLAYER);

            Log.d(LOG_TAG, "Loading saved DetailViewState: " + detailViewState.toString());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(LOG_TAG, "Saving state");
        outState.putString(DESCRIPTION, detailViewState.getDescription());
        outState.putString(VIDEO, detailViewState.getVideoUrl());
        outState.putString(THUMBNAIL, detailViewState.getThumbnailUrl());
        outState.putBoolean(SAMEPLAYER, true);

        if (playerView.getPlayer() != null) {
            outState.putLong(POSITION, playerView.getPlayer().getCurrentPosition());
        }
    }

    private void setDescription(String descriptionString, boolean hasDescription) {
        if (hasDescription) {
            description.setText(descriptionString);
        } else {
            description.setVisibility(View.GONE);
        }
    }

    private void setVideoAndView(String videoUrl, boolean hasVideo) {
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

    private void setThumbnailAndView(String imageUrl, boolean hasThumbnail) {
        if (hasThumbnail) {
            Log.d(LOG_TAG, "Preparing thumbnail with: " + imageUrl);
            recipeViewModel.loadImage(thumbnail, imageUrl);
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }

    public void setViewState(ViewState viewState) {
        if (viewState.getClass() == DetailViewState.class) {
            detailViewState = (DetailViewState) viewState;
        }
        updateViews();
    }

    private void updateViews() {
        //just to test, if description is null, most likely the fragment isn't finished creating
        if (description != null && detailViewState != null) {
            setDescription(detailViewState.getDescription(), detailViewState.hasDescription());
            setThumbnailAndView(detailViewState.getThumbnailUrl(), detailViewState.hasThumbnail());
            setVideoAndView(detailViewState.getVideoUrl(), detailViewState.hasVideoUrl());
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_step_detail;
    }
}
