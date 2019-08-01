package com.template.app.screen.userFavorite

import androidx.lifecycle.MediatorLiveData
import com.template.app.base.BaseViewModel
import com.template.app.data.model.User
import com.template.app.data.source.repositories.AppDBRepository
import com.template.app.util.liveData.Resource
import com.template.app.util.liveData.Status
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