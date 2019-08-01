package com.template.app.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob: Job? by lazy { Job() }

    /**
     * This is the scope for all coroutines launched by [ViewModel].
     *
     * Since we pass [viewModelJob], you can cancel all coroutines launched by [viewModelScope] by calling
     * viewModelJob.cancel().  This is called in [onCleared].
     */
    val viewModelScope: CoroutineScope? by lazy {
        CoroutineScope(Dispatchers.Main + viewModelJob as Job)
    }

    private val compositeDisposable = CompositeDisposable()

    fun launchDisposable(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        if (viewModelScope != null) {
            viewModelJob?.cancel()
        }
    }

}