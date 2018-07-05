package com.aboutblank.baking_app.di.modules;

import android.content.Context;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class ExoPlayerModule {
    private static String userAgent;

    public ExoPlayerModule(String userAgent) {
        ExoPlayerModule.userAgent = userAgent;
    }

    @Provides
    public static BandwidthMeter providesBandwidthMeter() {
        return new DefaultBandwidthMeter();
    }

    @Provides
    public static TrackSelection.Factory providesTrackSelectionFactory(BandwidthMeter bandwidthMeter) {
        return new AdaptiveTrackSelection.Factory(bandwidthMeter);
    }

    @Provides
    public static TrackSelector providesTrackSelector(TrackSelection.Factory trackSelectionFactory) {
        return new DefaultTrackSelector(trackSelectionFactory);
    }

    @Provides
    public static DataSource.Factory providesDataSourceFactory(Context context) {
        return new DefaultDataSourceFactory(context, userAgent);
    }

    @Provides
    public static ExtractorMediaSource.Factory providesExtractorMediaSource(DataSource.Factory dataSourceFactory) {
        return new ExtractorMediaSource.Factory(dataSourceFactory);
    }
}
