package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class IntroViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.intro_view)
    View layout;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.intro_description)
    TextView description;

    @BindView(R.id.intro_player)
    MediaPlayerView playerView;

    private PlayerHandler playerHandler;
    private IRecipeHolderListener recipeHolderListener;
    private MainViewModel mainViewModel;

    private String videoUrl;
    private boolean expanded = true;

    public IntroViewHolder(View view,
                           MainViewModel mainViewModel,
                           IRecipeHolderListener recipeHolderListener,
                           CompositeDisposable compositeDisposable) {
        super(view);
        this.recipeHolderListener = recipeHolderListener;
        ButterKnife.bind(this, view);
        this.mainViewModel = mainViewModel;

        expandableLayout.setOnExpansionUpdateListener(recipeHolderListener);
        playerHandler = new PlayerHandler(playerView, compositeDisposable);
        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        expandableLayout.toggle();
//        playerHandler.pause();
        recipeHolderListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step intro = recipe.getSteps().get(0);
        description.setText(intro.getDescription());

        boolean hasVideo = setVideoAndView(intro.getVideoUrl());
        if(hasVideo) {
            playerHandler.preparePlayer(mainViewModel.getPlayer(), videoUrl);
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

    @Override
    public void expand(boolean expand) {
        expanded = expand;
        expandableLayout.toggle();
        playerHandler.pause();
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }
}
