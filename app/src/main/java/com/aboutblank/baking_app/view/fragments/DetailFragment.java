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

public class DetailFragment extends BaseFragment {
    private final static String DESCRIPTION = "description";
    private final static String VIDEO = "video";
    private final static String IMAGE = "image";

    @BindView(R.id.detail_description)
    TextView description;

    @BindView(R.id.detail_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.detail_player)
    MediaPlayerView playerView;

    private RecipeViewModel recipeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    public void setRecipeViewModel(RecipeViewModel recipeViewModel) {
        this.recipeViewModel = recipeViewModel;
    }

    private boolean setDescription(String descriptionString) {
        boolean hasDescription = false;
        if (descriptionString != null && !descriptionString.isEmpty()) {
            hasDescription = true;
            description.setText(descriptionString);
        }

        return hasDescription;
    }

    private boolean setVideoAndView(String videoUrl) {
        boolean hasVideo = false;
        if (videoUrl != null && !videoUrl.isEmpty()) {
            hasVideo = true;
        } else {
            playerView.setVisibility(View.GONE);
        }

        return hasVideo;
    }

    private boolean setThumbnailAndView(String imageUrl) {
        boolean hasImage = false;
        if (recipeViewModel != null && imageUrl != null && !imageUrl.isEmpty()) {
            recipeViewModel.loadImage(thumbnail, imageUrl);
            hasImage = true;
        } else {
            thumbnail.setVisibility(View.GONE);
        }

        return hasImage;
    }

    public void setViewState(DetailViewState viewState) {

    }

    @Override
    public int getLayout() {
        return R.layout.frag_detail;
    }
}
