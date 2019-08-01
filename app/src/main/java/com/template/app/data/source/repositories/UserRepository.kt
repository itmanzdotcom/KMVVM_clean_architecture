package com.template.app.data.source.repositories

import com.template.app.data.model.Token
import com.template.app.data.model.User
import com.template.app.data.source.local.sharedprf.SharedPrefsApi
import com.template.app.data.source.local.sharedprf.SharedPrefsKey
import com.template.app.data.source.remote.service.AppApi
import com.google.gson.Gson
import io.reactivex.Single

interface UserRepository {

    fun getUserLocal(): User?

    fun saveUserToLocal(user: User)

    fun getUserFromLocal(): User?

    fun searchRepository(query: String?, page: Int): Single<List<User>>

    fun clearUserLocal()

    fun clearLocalData()

    fun doLogin(email: String, password: String): Single<Token>

    fun getUserProfile(): Single<User>

    fun forgetPassword(email: String): Single<Boolean>

    fun updateProfile(username: String): Single<Boolean>

}

class UserRepositoryImpl
constructor(private val api: AppApi, private val sharedPrefsApi: SharedPrefsApi,
        private val apiService: AppApi) : UserRepository {

    private val gson = Gson()

    val user = User()

    override fun saveUserToLocal(user: User) {
        val data = gson.toJson(user)
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, data)
    }

    override fun getUserFromLocal(): User? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_USER, User::class.java)
    }

    override fun searchRepository(query: String?, page: Int): Single<List<User>> {
        return api.searchRepository(query, page).map { value -> value.items }
    }

    override fun getUserLocal(): User? {
        return sharedPrefsApi.get(SharedPrefsKey.KEY_USER, User::class.java)
    }

    override fun clearLocalData() {
        sharedPrefsApi.clear()
    }

    override fun clearUserLocal() {
        sharedPrefsApi.clearKey(SharedPrefsKey.KEY_USER)
    }

    override fun doLogin(email: String, password: String): Single<Token> {
        return apiService.doLogin(email, password)
    }

    override fun getUserProfile(): Single<User> {
        return apiService.getUserProfile()
    }

    override fun forgetPassword(email: String): Single<Boolean> {
        return apiService.forgetPassword(email)
    }

    override fun updateProfile(username: String): Single<Boolean> {
        return apiService.updateProfile(username).map { it.success }
    }
}
