package com.aboutblank.baking_app.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface ISchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler ui();
}
