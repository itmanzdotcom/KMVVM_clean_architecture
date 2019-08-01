package com.cashless.self_order.util

import android.util.Log
import com.cashless.self_order.BuildConfig

object LogUtils {

  private val DEBUG = BuildConfig.DEBUG

  fun d(tag: String, message: String) {
    if (com.cashless.self_order.util.LogUtils.DEBUG) {
      Log.d(tag, message)
    }
  }

  fun e(tag: String, msg: String) {
    if (com.cashless.self_order.util.LogUtils.DEBUG) {
      Log.e(tag, msg)
    }
  }

  fun e(tag: String, msg: String, throwable: Throwable) {
    if (com.cashless.self_order.util.LogUtils.DEBUG) {
      Log.e(tag, msg, throwable)
    }
  }
}