package com.cashless.self_order.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.util.*

class KeyboardManager {
    companion object {

        /**
         * Hides the soft keyboard
         */
        fun hideSoftKeyboard(context: Activity) {
            if (context.currentFocus != null) {
                val inputMethodManager = context.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(context.currentFocus!!.windowToken,
                        0)
            }
        }

        fun forceHideSoftKeyboard(context: Context) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (imm.isActive) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
        }

        /**
         * Shows the soft keyboard
         */
        fun showSoftKeyboard(view: View?) {
            if (view != null) {
                val inputMethodManager = view.context
                        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                view.requestFocus()
                inputMethodManager.showSoftInput(view, 0)
            }
        }

        fun forceHideKeyboard(context: Context) {
            if (context is Activity) {
                hideSoftKeyboard(context)
            } else {
                forceHideSoftKeyboard(context)
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        fun setupDismissKeyBoard(context: Activity, view: View) {
            // Set up touch listener for non-text box views to hide keyboard.
            if (view !is EditText) {
                view.setOnTouchListener { _, _ ->
                    hideKeyboard(context)
                    false
                }
            }

            //If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    setupDismissKeyBoard(context, innerView)
                }
            }
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            Objects.requireNonNull(imm)
                    .hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }

        fun showForcedSoftKeyboard(activity: Activity, view: View) {
            if (view.requestFocus()) {
                val imm = activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
            }
        }
    }
}//No-Op
