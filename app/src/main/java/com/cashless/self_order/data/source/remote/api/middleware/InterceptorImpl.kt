package com.cashless.self_order.data.source.remote.api.middleware

import androidx.annotation.NonNull
import com.cashless.self_order.BuildConfig
import com.cashless.self_order.data.source.repositories.TokenRepository
import com.cashless.self_order.util.Constants
import com.cashless.self_order.util.LogUtils
import com.cashless.self_order.util.extension.insert
import com.cashless.self_order.util.extension.notNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection

class InterceptorImpl(private var tokenRepository: TokenRepository?) : Interceptor {

    companion object {
        private const val TAG = "InterceptorImpl"
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"
    }

    private var mIsRefreshToken: Boolean = false

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response? {
        //TODO check connection

        val builder = initializeHeader(chain)
        var request = builder.build()
        var response = chain.proceed(request)

        if (!mIsRefreshToken && response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {

            //TODO refresh Token

            builder.removeHeader(
                    KEY_TOKEN)

            tokenRepository?.getToken()?.accessToken.notNull { accessToken ->
                builder.addHeader(
                        KEY_TOKEN, TOKEN_TYPE + accessToken)
                request = builder.build()
                response = chain.proceed(request)
            }
        }

        return response
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest = chain.request()

        val urlWithPrefix = BuildConfig.BASE_URL + Constants.API_PREFIX
        val newUrl = chain.request().url().toString().insert(index = urlWithPrefix.length,
                contentInsert = "")
        LogUtils.e("newUrl", newUrl)
        val builder = originRequest.newBuilder()
                .url(newUrl)
                .header("Accept", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Cache-Control", "no-store")
                .method(originRequest.method(), originRequest.body())


        tokenRepository?.getToken()?.accessToken.notNull { accessToken ->
            builder.addHeader(KEY_TOKEN, TOKEN_TYPE + accessToken)
        }

        return builder
    }

    //TODO refreshToken
    /* private fun refreshToken() {
       if (tokenRepository == null) {
         return
       }
       mIsRefreshToken = true
       tokenRepository!!.refreshToken().subscribe(object : DisposableSingleObserver<LoginResponse>() {
         override fun onSuccess(loginResponse: LoginResponse?) {
           if (loginResponse != null && loginResponse!!.getToken() != null) {
             tokenRepository!!.saveToken(loginResponse!!.getToken())
           }
           mIsRefreshToken = false
         }

         override fun onError(e: Throwable) {
           LogUtils.e(TAG, "refreshToken", e)
           mIsRefreshToken = false
         }
       })
     }*/
}