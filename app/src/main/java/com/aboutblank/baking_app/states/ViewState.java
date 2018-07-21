package com.aboutblank.baking_app.states;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ViewState {
    private @State int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EXTENDED, COLLAPSED})
    @interface State {
    }

    public static final int EXTENDED = 0;
    public static final int COLLAPSED = 1;
}
