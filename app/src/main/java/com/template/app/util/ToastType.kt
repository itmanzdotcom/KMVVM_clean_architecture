package com.template.app.util

import androidx.annotation.StringDef

/**
 * ---------------------------
 * Created by [Android Lovers] on 1/9/19
 * Screen Name: Common
 * TODO: <Add a class header comment!>
 * ---------------------------
 */

interface ToastType {
    @StringDef(SUCCESS, ERROR, INFO, WARNING)
    annotation class Toast

    companion object {
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
        const val INFO = "INFO"
        const val WARNING = "WARNING"
    }
}