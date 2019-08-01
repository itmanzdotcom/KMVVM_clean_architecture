package com.cashless.self_order.screen.userFavorite

import androidx.lifecycle.MediatorLiveData
import com.cashless.self_order.base.BaseViewModel
import com.cashless.self_order.data.model.User
import com.cashless.self_order.data.source.repositories.AppDBRepository
import com.cashless.self_order.util.liveData.Resource
import com.cashless.self_order.util.liveData.Status
import kotlinx.coroutines.launch

class UserFavoriteViewModel(
        private val appDB: AppDBRepository) : BaseViewModel() {

    var repoList = MediatorLiveData<Resource<MutableList<User>>>()

    init {
        viewModelScope?.launch {
            repoList.addSource(appDB.getUsers()) { response ->
                repoList.value = Resource.multiStatus(Status.LOADING, response.toMutableList())
            }
        }
    }

    fun pressFavorite(user: User) {
        viewModelScope?.launch {
            appDB.insertOrUpdateUser(user)
        }
    }
}