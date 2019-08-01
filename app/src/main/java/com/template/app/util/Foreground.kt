package com.template.app.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import java.util.concurrent.CopyOnWriteArrayList


class Foreground : Application.ActivityLifecycleCallbacks {

    private var foreground = false
    private var paused = false
    private val listeners = CopyOnWriteArrayList<Listener>()
    private val handler = Handler()
    private var check: Runnable? = null

    fun get(): Foreground {
        return instance
    }

    operator fun get(application: Application): Foreground {
        return instance
    }

    operator fun get(ctx: Context): Foreground {
        return instance
    }


    fun isForeground(): Boolean {
        return foreground
    }

    fun isBackground(): Boolean {
        return !foreground
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    override fun onActivityPaused(activity: Activity?) {
        paused = true

        if (check != null) handler.removeCallbacks(check)

        handler.postDelayed(Runnable {
            if (foreground && paused) {
                foreground = false
                LogUtils.d(TAG, "Went background")
                for (listener in listeners) {
                    try {
                        listener.onBecameBackground()
                    } catch (exc: Exception) {
                        LogUtils.e(TAG, "Listener threw exception!", exc)
                    }
                }
                return@Runnable
            }
            LogUtils.d(TAG, "Still foreground")
        }, CHECK_DELAY)
    }

    override fun onActivityResumed(activity: Activity?) {
        paused = false

        val wasBackground = !foreground

        foreground = true

        if (check != null) handler.removeCallbacks(check)

        if (wasBackground) {
            LogUtils.d(TAG, "Went foreground")
            for (listener in listeners) {
                try {
                    listener.onBecameForeground()
                } catch (exc: Exception) {
                    LogUtils.e(TAG, "Listener threw exception!", exc)
                }
            }
            return
        }
        LogUtils.d(TAG, "still foreground")
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }


    interface Listener {

        fun onBecameForeground()

        fun onBecameBackground()
    }

    companion object {
        private const val TAG = "Foreground"
        private const val CHECK_DELAY: Long = 500

        lateinit var instance: Foreground

        /**
         * Its not strictly necessary to use this method - _usually_ invoking
         * get with a Context gives us a path to retrieve the Application and
         * initialise, but sometimes (e.g. in test harness) the ApplicationContext
         * is != the Application, and the docs make no guarantees.
         *
         * @return an initialised Foreground instance
         */
        fun inject(application: Application): Foreground {
            instance = Foreground()
            application.registerActivityLifecycleCallbacks(instance)
            return instance
        }

    }

}