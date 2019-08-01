package com.template.app

import android.app.Activity
import android.app.Application
import com.template.app.di.appModule
import com.template.app.di.networkModule
import com.template.app.di.repositoryModule
import com.template.app.di.viewModelModule
import com.template.app.di.UserAuthManager
import com.template.app.util.Foreground
import com.template.app.util.GlideApp
import com.template.app.util.LogUtils
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application(), Foreground.Listener {

    private val userAuthManager: UserAuthManager by inject()

    private var currentClass: Class<*>? = null

    var reLoginListener: ReLoginListener? = null

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        configLeakCanary()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            androidFileProperties()
            modules(appModule, networkModule, viewModelModule, repositoryModule)
        }
    }

    override fun onLowMemory() {
        GlideApp.get(this).onLowMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        GlideApp.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }

    override fun onBecameBackground() {
        LogUtils.d(TAG, "onBecameForeground")
    }

    override fun onBecameForeground() {
        LogUtils.d(TAG, "onBecameBackground")
    }

    private fun configLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }
    }

    fun setCurrentClass(clazz: Class<out Activity>) {
        currentClass = clazz
        LogUtils.d("CurrentClass: ", clazz.javaClass.simpleName)
    }

    fun getCurrentClass(): Class<*>? {
        return currentClass
    }

    fun registerReLoginListener(listener: ReLoginListener?) {
        reLoginListener = listener
    }

    fun unRegisterReLoginListener() {
        reLoginListener = null
    }

    fun onLogout() {
        userAuthManager.sendUserLogOutSuccess()
    }

    companion object {
        private const val TAG = "MainApplication"
        lateinit var sInstance: MainApplication
    }

    interface ReLoginListener {
        fun onReLogin()
    }
}