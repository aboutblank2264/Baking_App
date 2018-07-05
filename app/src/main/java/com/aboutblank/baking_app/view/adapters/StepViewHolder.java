package com.aboutblank.baking_app.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.player.MediaPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

class StepViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.step_short_description)
    TextView shortDescription;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.step_description)
    TextView fullDescription;

    @BindView(R.id.step_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.step_player)
    PlayerView playerView;

    private String videoUrl;
    private String thumbnailUrl;

    private Single<MediaPlayer> mediaPlayerObservable;
    private CompositeDisposable compositeDisposable;

    StepViewHolder(View view, Single<MediaPlayer> mediaPlayerObservable, CompositeDisposable compositeDisposable) {
        super(view);
        ButterKnife.bind(this, view);

        this.mediaPlayerObservable = mediaPlayerObservable;
        this.compositeDisposable = compositeDisposable;

        shortDescription.setOnClickListener(this);
    }

    void setStep(Step step) {
        shortDescription.setText(step.getShortDescription());
        fullDescription.setText(step.getDescription());
        setThumbnail(step.getThumbnailUrl());
        boolean saveToExpand = setVideoUrl(step.getVideoUrl());

        if(saveToExpand) {
            expandableLayout.expand(false);
            shortDescription.setClickable(false);
        }
    }

    void setThumbnail(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            thumbnailUrl = imageUrl;
        } else {
            thumbnail.setVisibility(View.GONE);
        }
    }

    boolean setVideoUrl(String videoUrl) {
        boolean isSafeToExpand = false;

        if (videoUrl != null && !videoUrl.isEmpty()) {
            this.videoUrl = videoUrl;
        } else {
            playerView.setVisibility(View.GONE);
            isSafeToExpand = true;
        }

        return isSafeToExpand;
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

                Disposable disposable = mediaPlayerObservable.subscribe(new Consumer<MediaPlayer>() {
                    @Override
                    public void accept(MediaPlayer mediaPlayer) {
                        mediaPlayer.prepare(videoUrl);
                    }
                });

                compositeDisposable.add(disposable);

            } else {
                playerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        if (state == ExpandableLayout.State.EXPANDING) {
//            recyclerView.smoothScrollToPosition(getAdapterPosition());
        }
    }

    @Override
    public void bindViewHolder() {

    }
}