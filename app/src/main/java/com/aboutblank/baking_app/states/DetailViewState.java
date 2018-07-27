package com.aboutblank.baking_app.states;

import android.text.TextUtils;

import com.aboutblank.baking_app.data.model.Step;

public class DetailViewState extends ViewState {
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private long currentPlaybackPosition;

    public DetailViewState(Builder builder) {
        shortDescription = builder.shortDescription;
        description = builder.description;
        videoUrl = builder.videoUrl;
        thumbnailUrl = builder.thumbnailUrl;
        currentPlaybackPosition = builder.currentPlaybackPosition;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public long getCurrentPlaybackPosition() {
        return currentPlaybackPosition;
    }

    public boolean hasShortDescription() {
        return checkNonNull(shortDescription);
    }

    public boolean hasDescription() {
        return checkNonNull(description);
    }

    public boolean hasVideoUrl() {
        return checkNonNull(videoUrl);
    }

    public boolean hasThumbnail() {
        return checkNonNull(thumbnailUrl);
    }

    private boolean checkNonNull(String string) {
        return !TextUtils.isEmpty(string);
    }

    @Override
    public String toString() {
        return "DetailViewState{" +
                "shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", currentPlaybackPosition='" + currentPlaybackPosition + '\'' +
                '}';
    }

    public static class Builder {
        private String shortDescription;
        private String description;
        private String videoUrl;
        private String thumbnailUrl;
        private long currentPlaybackPosition;

        public Builder() {
        }

        public Builder(Step step) {
            this.shortDescription = step.getShortDescription();
            this.description = step.getDescription();
            this.videoUrl = step.getVideoUrl();
            this.thumbnailUrl = step.getThumbnailUrl();
        }

        public Builder setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setCurrentPlaybackPosition(long currentPlaybackPosition) {
            this.currentPlaybackPosition = currentPlaybackPosition;
            return this;
        }

        public DetailViewState build() {
            return new DetailViewState(this);
        }
    }
}
