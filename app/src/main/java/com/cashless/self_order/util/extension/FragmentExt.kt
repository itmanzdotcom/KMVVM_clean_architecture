package com.cashless.self_order.util.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.cashless.self_order.MainApplication
import com.cashless.self_order.R
import com.cashless.self_order.util.AnimateType
import com.cashless.self_order.util.AppUtils.getKeyboardHeight

fun Fragment.replaceFragment(@IdRes containerId: Int, fragment: Fragment,
        addToBackStack: Boolean = true, tag: String = fragment::class.java.simpleName,
        animateType: AnimateType = AnimateType.FADE) {
    fragmentManager?.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }, animateType = animateType)
}

fun Fragment.addFragment(@IdRes containerId: Int, fragment: Fragment,
        addToBackStack: Boolean = true,
        tag: String = fragment::class.java.simpleName,
        animateType: AnimateType = AnimateType.FADE) {
    fragmentManager?.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun Fragment.addChildFragment(@IdRes containerId: Int, fragment: Fragment,
        fragmentManager: FragmentManager,
        addToBackStack: Boolean = true,
        tag: String = fragment::class.java.simpleName,
        animateType: AnimateType = AnimateType.FADE) {
    fragmentManager.transact({
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }, animateType = animateType)
}

fun Fragment.isCanPopBackStack(): Boolean {
    fragmentManager.notNull {
        val isShowPreviousPage = it.backStackEntryCount > 0
        if (isShowPreviousPage) {
            it.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
    return false
}

fun isExistFragment(fragmentManager: FragmentManager, tag: String): Boolean {
    val fragment = fragmentManager.findFragmentByTag(tag)
    return fragment != null
}


/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit,
        animateType: AnimateType = AnimateType.FADE) {
    beginTransaction().apply {
        setCustomAnimations(this, animateType)
        action()
    }.commitAllowingStateLoss()
}

fun setCustomAnimations(transaction: FragmentTransaction,
        animateType: AnimateType = AnimateType.FADE) {
    when (animateType) {
        AnimateType.FADE -> {
            transaction.setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
        }
        AnimateType.SLIDE_TO_RIGHT -> {
        }
        AnimateType.BOTTOM_UP -> {
            transaction.setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
        }
        AnimateType.SLIDE_TO_LEFT -> {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                    R.anim.enter_from_left, R.anim.exit_to_right)
        }
    }
}

/**
 * If no window token is found, keyboard is checked using reflection to know if keyboard visibility toggle is needed
 *
 * @param useReflection - whether to use reflection in case of no window token or not
 */
fun Fragment.hideKeyboard(context: Context = MainApplication.sInstance,
        useReflection: Boolean = true) {
    val windowToken = view?.rootView?.windowToken
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    windowToken?.let {
        imm.hideSoftInputFromWindow(windowToken, 0)
    } ?: run {
        if (useReflection) {
            try {
                if (getKeyboardHeight(imm) > 0) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            } catch (exception: Exception) {
            }
        }
    }
}