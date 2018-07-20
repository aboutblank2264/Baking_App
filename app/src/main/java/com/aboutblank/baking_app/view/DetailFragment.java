package com.aboutblank.baking_app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.player.MediaPlayerView;

import butterknife.BindView;

public class DetailFragment extends BaseFragment {

    @BindView(R.id.detail_thumbnail)
    ImageView thumbnail;

    @BindView(R.id.detail_player)
    MediaPlayerView playerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    @Override
    public int getLayout() {
        return R.layout.frag_detail;
    }
}
