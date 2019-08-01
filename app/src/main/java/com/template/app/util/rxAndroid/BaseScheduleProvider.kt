package com.template.app.util.rxAndroid

import androidx.annotation.NonNull
import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    @NonNull
    fun computation(): Scheduler

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler
}
