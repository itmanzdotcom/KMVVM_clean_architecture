package com.template.app.di

import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.template.app.data.source.local.dao.AppDatabase
import com.template.app.data.source.local.sharedprf.SharedPrefsApi
import com.template.app.data.source.local.sharedprf.SharedPrefsImpl
import com.template.app.data.source.remote.api.middleware.BooleanAdapter
import com.template.app.data.source.remote.api.middleware.DoubleAdapter
import com.template.app.data.source.remote.api.middleware.IntegerAdapter
import com.template.app.util.rxAndroid.BaseSchedulerProvider
import com.template.app.util.rxAndroid.SchedulerProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * --------------------
 * Created by [Android Lovers] on 6/17/2019.
 * Screen name:
 * --------------------
 */

val appModule = module {
    single { provideResources(androidApplication()) }
    single { provideSharedPrefsApi(androidApplication()) }
    single { provideBaseSchedulerProvider() }
    single { provideGson() }
    single { provideAppDatabase(androidApplication()) }
}

fun provideResources(app: Application): Resources {
    return app.resources
}

fun provideSharedPrefsApi(app: Application): SharedPrefsApi {
    return SharedPrefsImpl(app)
}

fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
    return SchedulerProvider()
}

fun provideGson(): Gson {
    val booleanAdapter = BooleanAdapter()
    val integerAdapter = IntegerAdapter()
    val doubleAdapter = DoubleAdapter()
    return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, booleanAdapter)
            .registerTypeAdapter(Int::class.java, integerAdapter)
            .registerTypeAdapter(Double::class.java, doubleAdapter)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
}

fun provideAppDatabase(app: Application): AppDatabase {
    return Room.databaseBuilder(app.applicationContext, AppDatabase::class.java,
            AppModuleConstants.DB_NAME)
            .fallbackToDestructiveMigration().build()
}

object AppModuleConstants {
    const val DB_NAME = "db_name"

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //No-Op
        }
    }
}