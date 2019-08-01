package com.template.app.screen.user

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import com.template.app.base.BaseViewModel
import com.template.app.data.model.User
import com.template.app.data.source.remote.api.error.RetrofitException
import com.template.app.data.source.repositories.AppDBRepository
import com.template.app.data.source.repositories.UserRepository
import com.template.app.util.Constants
import com.template.app.util.extension.notNull
import com.template.app.util.extension.withScheduler
import com.template.app.util.liveData.Resource
import com.template.app.util.liveData.SingleLiveEvent
import com.template.app.util.liveData.Status
import com.template.app.util.rxAndroid.BaseSchedulerProvider
import io.reactivex.Observable
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class UserViewModel(
        private val baseSchedulerProvider: BaseSchedulerProvider,
        private val userRepository: UserRepository,
        private val appDb: AppDBRepository) : BaseViewModel() {

    var query = MutableLiveData<String>()
    var repoList = SingleLiveEvent<Resource<MutableList<User>>>()

    fun searchUser(status: Status, page: Int = Constants.PAGE_DEFAULT) {
        launchDisposable {
            userRepository.searchRepository(query.value, page)
                    .map { response ->
                        val data = repoList.value?.data
                        data.notNull {
                            if (status == Status.REFRESH_DATA) {
                                it.clear()
                            }
                            it.addAll(response)
                            return@map data
                        }
                        return@map response.toMutableList()
                    }
                    .withScheduler(baseSchedulerProvider)
                    .subscribe(
                            { data ->
                                repoList.value = Resource.multiStatus(status, data)
                            },
                            { throwable ->
                                if (throwable is RetrofitException) {
                                    repoList.value = Resource.error(throwable)
                                }
                            })
        }
    }

    fun initRxSearch(editText: EditText) {
        launchDisposable {
            com.template.app.util.RxView.search(editText)
                    .skip(1)
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .switchMap { query ->
                        userRepository.searchRepository(query, Constants.PAGE_DEFAULT)
                                .toObservable()
                                .onErrorResumeNext(Observable.empty())
                    }
                    .withScheduler(baseSchedulerProvider)
                    .subscribe(
                            { data ->
                                repoList.value = Resource.searchData(data.toMutableList())
                            },
                            { throwable ->
                                initRxSearch(editText)
                                if (throwable is RetrofitException) {
                                    repoList.value = Resource.error(throwable)
                                }
                            })
        }
    }

    fun pressFavorite(user: User) {
        viewModelScope?.launch {
            appDb.insertOrUpdateUser(user)
        }
    }
}