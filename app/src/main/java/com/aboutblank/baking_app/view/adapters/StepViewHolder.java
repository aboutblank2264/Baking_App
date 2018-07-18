package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.player.MediaPlayerView;
import com.aboutblank.baking_app.view.PlayerHandler;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class StepViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

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

    private PlayerHandler playerHandler;
    private MainViewModel mainViewModel;

    public StepViewHolder(View view, MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;

        playerHandler = new PlayerHandler(playerView, compositeDisposable);
        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean isExpanded = expandableLayout.isExpanded();

        expand(isExpanded);
    }

    private void expand(boolean isExpanded) {
        hideOrShowEllipses(isExpanded);
        expandableLayout.toggle();
        playerHandler.pause();
        showPlayer();
    }

    private void showPlayer() {

        boolean videoToPlay = playerHandler.preparePlayer(mainViewModel.getPlayer(),
               videoUrl);

        if(!videoToPlay) {
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
    public void onExpansionUpdate(float expansionFraction, int state) {
        if (state == ExpandableLayout.State.EXPANDING) {
//            recyclerView.smoothScrollToPosition(getAdapterPosition());
        }
    }

    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step step = recipe.getSteps().get(position);

//        shortDescription.setText(step.getShortDescription());
        fullDescription.setText(step.getDescription());
        setThumbnail(step.getThumbnailUrl());
        videoUrl = step.getVideoUrl();
    }

    private void setThumbnail(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            mainViewModel.getImageUtils().loadImage(thumbnail, imageUrl);
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }
}