package com.template.app.util

import android.app.Activity
import android.util.DisplayMetrics

object DeviceUtils {

    fun getScreenSize(activity: Activity?): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics
    }

}