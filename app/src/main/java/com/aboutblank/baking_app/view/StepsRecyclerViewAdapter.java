package com.aboutblank.baking_app.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.utils.ImageUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.StepsViewHolder> {
    private List<Step> steps;
    private ItemClickedListener itemClickedListener;

    private ExoPlayer player;
    private DataSource.Factory dataSourceFactory;

    public StepsRecyclerViewAdapter(List<Step> steps, ItemClickedListener itemClickedListener) {
        this.steps = steps;
        this.itemClickedListener = itemClickedListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_recycler_item, parent, false);

        buildPlayer(parent.getContext());

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        String videoUrl = steps.get(position).getVideoUrl();
        String thumbnail = steps.get(position).getThumbnailUrl();
        String shortDescription = steps.get(position).getShortDescription();
        if(videoUrl != null && !videoUrl.isEmpty()) {
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl));
            player.prepare(mediaSource);
            holder.setPlayer(player);
        } else if(thumbnail != null && !thumbnail.isEmpty()){
            holder.setThumbnail(thumbnail);
        }

        holder.setShortDescription(shortDescription);
    }

    private void buildPlayer(@NonNull Context context) {
        //Create ExoPlayer
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector(trackSelectionFactory));

        //Create DataSource Factory
        dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getString(R.string.app_name)));
    }

    public void releasePlayer() {
        player.release();
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void update(List<Step> newSteps) {
        steps.clear();
        steps.addAll(newSteps);

        notifyDataSetChanged();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_thumbnail)
        ImageView thumbnail;

        @BindView(R.id.card_player)
        PlayerView playerView;

        @BindView(R.id.card_short_description)
        TextView shortDescription;

        StepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        void setShortDescription(@NonNull String description) {
            shortDescription.setText(description);
        }

        void setThumbnail(@NonNull String url) {
            ImageUtils.loadImage(thumbnail, url);
        }

        void setPlayer(@NonNull ExoPlayer player) {
            //If there is a video, hide the thumbnail view just in case.
            thumbnail.setVisibility(View.GONE);
            playerView.setPlayer(player);
        }

        @Override
        public void onClick(View v) {
            itemClickedListener.onItemClick(v, getAdapterPosition());
        }
    }
}
