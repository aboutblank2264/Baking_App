package com.aboutblank.baking_app.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aboutblank.baking_app.BakingApplication;
import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;

public class StepDetailFragment extends BaseFragment {

    private final String LOG_TAG = getClass().getSimpleName();

    private final static String ID = "id";
    private final static String DESCRIPTION = "description";
    private final static String THUMBNAIL_URL = "thumbnail";
    private final static String VIDEO_URL = "player";

    @BindView(R.id.step_layout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.step_player)
    PlayerView player;
    @BindView(R.id.step_thumbnail)
    ImageView thumbnailView;
    @BindView(R.id.step_description)
    TextView descriptionView;

    private String description;
    private String thumbnailUrl;
    private String videoUrl;

    private MainViewModel mainViewModel;

    private ExoPlayer exoPlayer;

    public static StepDetailFragment newInstance(@NonNull Step step) {

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DESCRIPTION, step.getDescription());
        bundle.putString(THUMBNAIL_URL, step.getThumbnailUrl());
        bundle.putString(VIDEO_URL, step.getVideoUrl());

        stepDetailFragment.setArguments(bundle);

        return stepDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            description = bundle.getString(DESCRIPTION);
            thumbnailUrl = bundle.getString(THUMBNAIL_URL);
            videoUrl = bundle.getString(VIDEO_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mainViewModel = ((BakingApplication) requireActivity().getApplication()).getMainViewModel();

        descriptionView.setText(description);

        setExoPlayer();

        return view;
    }

    private void setExoPlayer() {
        if (videoUrl != null && !videoUrl.isEmpty()) {

            //Remove image view as it is not needed
            thumbnailView.setVisibility(View.GONE);

            exoPlayer = mainViewModel.getExoPlayer();

            MediaSource mediaSource = mainViewModel.getMediaSourceFactory()
                    .createMediaSource(Uri.parse(videoUrl));

            //Prepare the video
            exoPlayer.prepare(mediaSource);

            player.setPlayer(exoPlayer);

        } else if (thumbnailUrl != null && !thumbnailUrl.isEmpty()) {
            player.setVisibility(View.GONE);

            // Else attempt to load a static image
            mainViewModel.getImageUtils().loadImage(thumbnailView, thumbnailUrl);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_step_detail;
    }
}
