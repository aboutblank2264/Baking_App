package com.aboutblank.baking_app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Details on implementing expandable CardViews from this post:
 * https://medium.com/@akshay.shinde/cardview-expand-collapse-cd10916bb77c
 */
public class ExpandableCardView extends CardView {
    private final String LOG_TAG = getClass().getSimpleName();

    private int screenHeight;
    private int minHeight;

    public ExpandableCardView(@NonNull Context context) {
        super(context);
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpandableCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenHeight = getScreenHeight(context);

        runJustBeforeBeingDrawn(() -> {
                    minHeight = getHeight();
                    screenHeight -= minHeight;
                    Log.d(LOG_TAG, String.format("Setting height values: %d, %d", screenHeight, minHeight));
                });
    }

    // https://stackoverflow.com/questions/3779173/determining-the-size-of-an-android-view-at-runtime
    public void runJustBeforeBeingDrawn(Runnable runnable) {
        final ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        };
        getViewTreeObserver().addOnPreDrawListener(preDrawListener);
    }


    // calculate the screen height, this is the maximum height the CardView can expand to.
    private int getScreenHeight(Context context) {
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dimension = new DisplayMetrics();
        if (windowmanager != null) {
            windowmanager.getDefaultDisplay().getMetrics(dimension);
        }

        return dimension.heightPixels;
    }

    // collapses or expands the view to the given height
    public void toggleView(boolean expand) {
        int height = expand ? screenHeight : minHeight;
        Log.d(LOG_TAG, "Toggling view, setting height to : " + height);

        ValueAnimator anim = ValueAnimator.ofInt(getMeasuredHeightAndState(),
                height);
        anim.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = val;
            setLayoutParams(layoutParams);

        });
        anim.start();
    }
}
