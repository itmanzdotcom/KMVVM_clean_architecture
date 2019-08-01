package com.template.app.util.extension

import androidx.lifecycle.MutableLiveData
import com.template.app.util.rxAndroid.BaseSchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Use SchedulerProvider configuration for Completable
 */
fun Completable.withScheduler(scheduler: BaseSchedulerProvider): Completable =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Single
 */
fun <T> Single<T>.withScheduler(scheduler: BaseSchedulerProvider): Single<T> =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Observable
 */
fun <T> Observable<T>.withScheduler(scheduler: BaseSchedulerProvider): Observable<T> =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

/**
 * Use SchedulerProvider configuration for Flowable
 */
fun <T> Flowable<T>.withScheduler(scheduler: BaseSchedulerProvider): Flowable<T> =
        this.subscribeOn(scheduler.io()).observeOn(scheduler.ui())

fun <T> Single<T>.loading(liveData: MutableLiveData<Boolean>) =
        doOnSubscribe { liveData.postValue(true) }.doFinally { liveData.postValue(false) }