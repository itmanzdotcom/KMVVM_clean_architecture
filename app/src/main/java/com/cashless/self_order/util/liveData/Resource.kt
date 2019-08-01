package com.cashless.self_order.util.liveData

import com.cashless.self_order.data.source.remote.api.error.RetrofitException


/**
 * A generic class that holds a value withScheduler its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val error: RetrofitException?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(error: RetrofitException): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> loadMore(data: T?): Resource<T> {
            return Resource(Status.LOAD_MORE, data, null)
        }

        fun <T> refreshData(data: T?): Resource<T> {
            return Resource(Status.REFRESH_DATA, data, null)
        }

        fun <T> searchData(data: T?): Resource<T> {
            return Resource(Status.SEARCH_DATA, data, null)
        }

        fun <T> multiStatus(status: Status, data: T?): Resource<T> {
            return Resource(status, data, null)
        }
    }
}
