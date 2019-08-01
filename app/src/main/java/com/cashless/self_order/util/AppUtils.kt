package com.cashless.self_order.util

import android.content.Context
import android.content.pm.PackageManager
import android.view.inputmethod.InputMethodManager


/**
 * ---------------------------
 * Created by [TOKUDA J.] on 1/17/19
 * Screen Name: Common
 * TODO: <Add a class header comment!>
 * ---------------------------
 */
object AppUtils {

    fun getKeyboardHeight(imm: InputMethodManager): Int = InputMethodManager::class.java.getMethod(
            "getInputMethodWindowVisibleHeight").invoke(imm) as Int

    fun isGoogleMapsInstalled(context: Context): Boolean {
        return try {
            context.packageManager.getApplicationInfo("com.google.android.apps.maps", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}