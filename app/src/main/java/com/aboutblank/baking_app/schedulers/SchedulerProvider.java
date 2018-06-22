package com.aboutblank.baking_app.schedulers;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides schedulers
 */
@Singleton
public class SchedulerProvider implements ISchedulerProvider {

    @Inject
    public SchedulerProvider() {
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
