package com.template.app.util.liveData

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI withScheduler its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    REFRESH_DATA,
    LOAD_MORE,
    SEARCH_DATA
}
