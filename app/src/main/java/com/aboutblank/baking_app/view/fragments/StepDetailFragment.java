package com.aboutblank.baking_app.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.player.MediaPlayerView;
import com.aboutblank.baking_app.states.DetailViewState;
import com.aboutblank.baking_app.viewmodels.RecipeViewModel;

import butterknife.BindView;

public class StepDetailFragment extends BaseFragment {
    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.detail_description)
    TextView description;

    @BindView(R.id.detail_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.detail_player)
    MediaPlayerView playerView;

    private RecipeViewModel recipeViewModel;
    private DetailViewState detailViewState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        playerView.getPlayer().release();
    }

    public void setRecipeViewModel(RecipeViewModel recipeViewModel) {
        this.recipeViewModel = recipeViewModel;
    }

    private void setDescription(String descriptionString, boolean hasDescription) {
        if(hasDescription) {
            description.setText(descriptionString);
        } else {
            description.setVisibility(View.GONE);
        }
    }

    private void setVideoAndView(String videoUrl, boolean hasVideo) {
        if (hasVideo) {
            //TODO
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void setThumbnailAndView(String imageUrl, boolean hasThumbnail) {
        if (hasThumbnail) {
            recipeViewModel.loadImage(thumbnail, imageUrl);
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }

    public void setViewState(DetailViewState viewState) {
        setDescription(viewState.getDescription(), viewState.hasDescription());
        setThumbnailAndView(viewState.getThumbnailUrl(), viewState.hasThumbnail());
        setVideoAndView(viewState.getVideoUrl(), viewState.hasVideoUrl());
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_step_detail;
    }
}
