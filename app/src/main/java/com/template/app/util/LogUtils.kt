package com.template.app.util

import android.util.Log
import com.template.app.BuildConfig

object LogUtils {

  private val DEBUG = BuildConfig.DEBUG

  fun d(tag: String, message: String) {
    if (com.template.app.util.LogUtils.DEBUG) {
      Log.d(tag, message)
    }
  }

  fun e(tag: String, msg: String) {
    if (com.template.app.util.LogUtils.DEBUG) {
      Log.e(tag, msg)
    }
  }

  fun e(tag: String, msg: String, throwable: Throwable) {
    if (com.template.app.util.LogUtils.DEBUG) {
      Log.e(tag, msg, throwable)
    }
  }
}