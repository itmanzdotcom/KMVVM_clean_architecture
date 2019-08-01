package com.cashless.self_order.data.source.repositories

import com.cashless.self_order.data.model.Token
import com.cashless.self_order.data.source.local.sharedprf.SharedPrefsApi
import com.cashless.self_order.data.source.local.sharedprf.SharedPrefsKey
import com.cashless.self_order.util.extension.notNull


interface TokenRepository {

    fun getToken(): Token?

    fun saveToken(token: Token)

    fun clearToken()

    fun setPrefixRole(prefixRole: String)

    fun getPrefixRole(): String

    fun setPrefixRoleTemp(prefixRoleTemp: String)

    fun isHasLogIn(): Boolean

    fun isVerifyEmail(): Boolean

    fun isVerifyPhone(): Boolean

    fun doLogout()
}

class TokenRepositoryImpl
constructor(private val sharedPrefsApi: SharedPrefsApi) : TokenRepository {
    private var tokenCache: Token? = null

    private var prefixRoleTemp = ""

    override fun getToken(): Token? {
        tokenCache.notNull {
            return it
        }

        val token = sharedPrefsApi.get(SharedPrefsKey.KEY_TOKEN, Token::class.java)
        token.notNull {
            tokenCache = it
            return it
        }

        return null
    }

    override fun saveToken(token: Token) {
        tokenCache = token
        sharedPrefsApi.put(SharedPrefsKey.KEY_TOKEN, tokenCache)
    }

    override fun clearToken() {
        tokenCache = null
        sharedPrefsApi.clear()
    }


    override fun setPrefixRole(prefixRole: String) {
        sharedPrefsApi.put(SharedPrefsKey.KEY_PREFIT_ROLE, prefixRole)
    }

    override fun getPrefixRole(): String {
        if (prefixRoleTemp.isNotEmpty()) {
            return prefixRoleTemp
        }
        val prefixRole = sharedPrefsApi.get(SharedPrefsKey.KEY_PREFIT_ROLE, String::class.java)
        if (!prefixRole.isBlank()) {
            return prefixRole
        }
        return USER_ROLE
    }

    override fun setPrefixRoleTemp(prefixRoleTemp: String) {
        this.prefixRoleTemp = prefixRoleTemp
    }

    override fun isHasLogIn(): Boolean {
        return !getToken()?.accessToken.isNullOrEmpty() && isVerifyEmail() && isVerifyPhone()
    }

    override fun isVerifyEmail(): Boolean {
        getToken().notNull {
            return it.emailVerified
        }
        return false
    }

    override fun isVerifyPhone(): Boolean {
        getToken().notNull {
            return it.phoneVerified
        }
        return false
    }

    override fun doLogout() {
        clearToken()
        setPrefixRoleTemp("")
        setPrefixRole("")
    }


    companion object {
        const val USER_ROLE = "user/"
    }
}