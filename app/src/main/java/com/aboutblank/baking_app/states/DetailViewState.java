package com.aboutblank.baking_app.states;

import com.aboutblank.baking_app.data.model.Step;

public class DetailViewState extends ViewState {
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public DetailViewState(Step step, @State int state) {
        setState(state);
        shortDescription = step.getShortDescription();
        description = step.getDescription();
        videoUrl = step.getVideoUrl();
        thumbnailUrl = step.getThumbnailUrl();
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
        return string != null && !string.isEmpty();
    }

    @Override
    public String toString() {
        return "DetailViewState{" +
                "shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", expanded=" + getState() +
                '}';
    }
}
