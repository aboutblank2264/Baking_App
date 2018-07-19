package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.player.MediaPlayerView;
import com.aboutblank.baking_app.view.IRecipeHolderListener;
import com.aboutblank.baking_app.view.PlayerHandler;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class StepViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.step_view)
    View layout;

    @BindView(R.id.step_description)
    TextView fullDescription;

    @BindView(R.id.step_ellipses)
    TextView ellipses;

//    @BindView(R.id.step_short_description)
//    TextView shortDescription;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.step_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.step_player)
    MediaPlayerView playerView;

    private String videoUrl;

    private IRecipeHolderListener recipeHolderListener;
    private PlayerHandler playerHandler;
    private MainViewModel mainViewModel;

    private boolean needToExpand = false;
    private boolean expanded = false;

    public StepViewHolder(View view,
                          MainViewModel mainViewModel,
                          IRecipeHolderListener recipeHolderListener,
                          CompositeDisposable compositeDisposable) {
        super(view);
        this.recipeHolderListener = recipeHolderListener;
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;

        expandableLayout.setOnExpansionUpdateListener(recipeHolderListener);
        expandableLayout.setInterpolator(new OvershootInterpolator());

        playerHandler = new PlayerHandler(playerView, compositeDisposable);
        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        boolean isExpanded = expandableLayout.isExpanded();
//
//        expand(isExpanded);
        recipeHolderListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public void expand(boolean expand) {
        expanded = expand;
        if (needToExpand) {
            hideOrShowEllipses(expand);
            expandableLayout.toggle();
            playerHandler.pause();
            showPlayer();
        }
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }

    private void showPlayer() {
        boolean videoToPlay = playerHandler.preparePlayer(mainViewModel.getPlayer(),
                videoUrl);

        if (!videoToPlay) {
            // if there's no video url, don't show the player.
            playerView.setVisibility(View.GONE);
        }
    }

    private void hideOrShowEllipses(boolean isExpanded) {
        if (isExpanded) {
            ellipses.setVisibility(View.GONE);
        } else {
            ellipses.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step step = recipe.getSteps().get(position);

//        shortDescription.setText(step.getShortDescription());
        fullDescription.setText(step.getDescription());
        boolean hasImage = setThumbnailAndView(step.getThumbnailUrl());
        boolean hasVideo = setVideoAndView(step.getVideoUrl());

        if (hasImage || hasVideo) {
            needToExpand = true;
        } else {
            ellipses.setVisibility(View.GONE);
        }
    }

    private boolean setVideoAndView(String videoUrl) {
        boolean hasVideo = false;
        if (videoUrl != null && !videoUrl.isEmpty()) {
            hasVideo = true;
            this.videoUrl = videoUrl;
        } else {
            playerView.setVisibility(View.GONE);
        }

        return hasVideo;
    }

    private boolean setThumbnailAndView(String imageUrl) {
        boolean hasImage = false;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            mainViewModel.getImageUtils().loadImage(thumbnail, imageUrl);
            hasImage = true;
        } else {
            thumbnail.setVisibility(View.GONE);
        }

        return hasImage;
    }
}