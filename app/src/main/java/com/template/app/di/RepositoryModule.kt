package com.template.app.di

import android.app.Application
import com.template.app.data.source.local.dao.AppDatabase
import com.template.app.data.source.local.sharedprf.SharedPrefsApi
import com.template.app.data.source.local.sharedprf.SharedPrefsImpl
import com.template.app.data.source.remote.service.AppApi
import com.template.app.data.source.repositories.*
import com.template.app.util.rxAndroid.BaseSchedulerProvider
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * --------------------
 * Created by [Android Lovers] on 6/17/2019.
 * Screen name:
 * --------------------
 */

val repositoryModule = module {
    single { provideAppDBRepository(get(), get()) }
    single { provideTokenRepository(androidApplication()) }
    single { provideUserRepository(get(), get()) }
    single { provideUserAuthRepository(get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepositoryImpl(
            SharedPrefsImpl(app))
}

fun provideAppDBRepository(appDatabase: AppDatabase, gson: Gson): AppDBRepository {
    return AppDBRepositoryImpl(appDatabase, gson)
}

fun provideUserRepository(api: AppApi, sharedPrefsApi: SharedPrefsApi): UserRepository {
    return UserRepositoryImpl(api,
            sharedPrefsApi, api)
}

fun provideUserAuthRepository(baseSchedulerProvider: BaseSchedulerProvider): UserAuthManager {
    return UserAuthManager(baseSchedulerProvider)
}