package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.aboutblank.baking_app.player.MediaPlayerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
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

    private CompositeDisposable compositeDisposable;
    private MainViewModel mainViewModel;

    public IntroViewHolder(View view, MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;
        this.compositeDisposable = compositeDisposable;

        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        expandableLayout.toggle();
        playerView.getPlayer().setPlayWhenReady(false);
    }

    private void preparePlayer(final String videoUrl) {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            subscribeToMedia(mainViewModel.getPlayer(), videoUrl);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void subscribeToMedia(Single<MediaPlayer> player, final String videoUrl) {
        compositeDisposable.add(player.subscribe(mediaPlayer -> {
            mediaPlayer.prepare(videoUrl);
            playerView.setPlayer(mediaPlayer);
        }));
    }

    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step intro = recipe.getSteps().get(0);
        description.setText(intro.getDescription());

        preparePlayer(recipe.getSteps().get(0).getVideoUrl());
    }
}
