package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import io.reactivex.disposables.Disposable;

public class IntroViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.intro_description)
    TextView description;

    @BindView(R.id.intro_player)
    MediaPlayerView playerView;

    private String videoUrl;

    private Single<MediaPlayer> mediaPlayerObservable;
    private CompositeDisposable compositeDisposable;
    private MainViewModel mainViewModel;

    public IntroViewHolder(View view, MainViewModel mainViewModel, CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;
        this.compositeDisposable = compositeDisposable;
    }

    private void subscribeToMedia(final String videoUrl) {
        compositeDisposable.add(mediaPlayerObservable.subscribe(mediaPlayer -> {
            mediaPlayer.prepare(videoUrl);
            playerView.setPlayer(mediaPlayer);
        }));
    }

    @Override
    public void onClick(View view) {
        if (expandableLayout.isExpanded()) {
            expandableLayout.collapse();
            handlePlayer(false);
        } else {
            expandableLayout.expand();

            handlePlayer(true);
        }
    }

    private void handlePlayer(boolean toExpand) {
        if (toExpand) {
            if (videoUrl != null && !videoUrl.isEmpty()) {
                Log.d(LOG_TAG, "Playing video: " + videoUrl);
                preparePlayer(videoUrl);
            } else {
                playerView.setVisibility(View.GONE);
            }
        }
    }

    private void preparePlayer(@NonNull final String videoUrl) {
        if(mediaPlayerObservable == null) {
            mediaPlayerObservable = mainViewModel.getPlayer();
        }
        Disposable disposable = mediaPlayerObservable.subscribe(mediaPlayer -> mediaPlayer.prepare(videoUrl));

        compositeDisposable.add(disposable);
    }

    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step intro = recipe.getSteps().get(0);
        description.setText(intro.getDescription());
        videoUrl = recipe.getSteps().get(0).getVideoUrl();
    }
}
