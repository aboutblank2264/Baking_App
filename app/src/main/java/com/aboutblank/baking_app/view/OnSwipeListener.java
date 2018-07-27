package com.aboutblank.baking_app.view;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * https://developer.android.com/training/custom-views/making-interactive
 * https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
 */
public abstract class OnSwipeListener implements View.OnTouchListener {

    private GestureDetector gestureDetector;

    public OnSwipeListener(Context context) {
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public abstract void onSwipeLeft();

    public abstract void onSwipeRight();

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private final int SWIPE_THRESHOLD = 100;
        private final int SWIPE_VELOCITY_THRESHOLD = 100;

        // must do this.
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false; // default if event is not consumed.
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();

            // check if it is a horizontal swipe
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                // check if the swipe is long/fast enough
                if (Math.abs(distanceX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    //decide if it was swiping left or right
                    if (distanceX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            return result;
        }
    }
}
