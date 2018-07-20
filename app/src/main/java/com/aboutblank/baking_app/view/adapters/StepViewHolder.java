package com.aboutblank.baking_app.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aboutblank.baking_app.MainViewModel;
import com.aboutblank.baking_app.R;
import com.aboutblank.baking_app.data.model.Recipe;
import com.aboutblank.baking_app.data.model.Step;
import com.aboutblank.baking_app.view.IRecipeHolderListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class StepViewHolder extends RecyclerView.ViewHolder
        implements IRecipeViewHolder, View.OnClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.step_view)
    View layout;

    @BindView(R.id.step_short_description)
    TextView shortDescription;


    private IRecipeHolderListener recipeHolderListener;
    private MainViewModel mainViewModel;

    private boolean needToExpand = false;
    private boolean expanded = false;

    public StepViewHolder(View view,
                          MainViewModel mainViewModel,
                          IRecipeHolderListener recipeHolderListener,
                          CompositeDisposable compositeDisposable) {
        super(view);
        this.recipeHolderListener = recipeHolderListener;
        ButterKnife.bind(this, view);

        this.mainViewModel = mainViewModel;

        layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        recipeHolderListener.onItemClick(view, getAdapterPosition());
    }

    @Override
    public void expand(boolean expand) {
    }

    @Override
    public boolean isExpanded() {
        return expanded;
    }


    @Override
    public void bindViewHolder(@NonNull Recipe recipe, int position) {
        Step step = recipe.getSteps().get(position);

        shortDescription.setText(step.getShortDescription());
    }

//    private boolean setVideoAndView(String videoUrl) {
//        boolean hasVideo = false;
//        if (videoUrl != null && !videoUrl.isEmpty()) {
//            hasVideo = true;
//            this.videoUrl = videoUrl;
//        } else {
//            playerView.setVisibility(View.GONE);
//        }
//
//        return hasVideo;
//    }
//
//    private boolean setThumbnailAndView(String imageUrl) {
//        boolean hasImage = false;
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            mainViewModel.getImageUtils().loadImage(thumbnail, imageUrl);
//            hasImage = true;
//        } else {
//            thumbnail.setVisibility(View.GONE);
//        }
//
//        return hasImage;
//    }
}