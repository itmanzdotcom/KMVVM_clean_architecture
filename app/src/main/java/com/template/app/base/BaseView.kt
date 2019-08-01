package com.template.app.base

import androidx.annotation.StringRes
import com.template.app.util.ToastType
import com.template.app.widget.dialogManager.DialogAlert
import com.template.app.widget.dialogManager.DialogConfirm
import io.reactivex.disposables.Disposable


/**
 * @author LongHV.
 */

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun onError(@StringRes resId: Int)
    fun handleApiError(apiError: Throwable)
    fun launchDisposable(job: () -> Disposable)

    // Showing dialog popup customize
    fun showDialogType(@ToastType.Toast toastType: String, message: String)

    fun showToastSuccess(message: String)
    fun showAlertDialog(title: String = "", message: String = "", titleButton: String = "",
            listener: DialogAlert.Companion.OnButtonClickedListener? = null)

    fun showConfirmDialog(title: String? = "", message: String? = "",
            titleButtonPositive: String = "", titleButtonNegative: String = "",
            listener: DialogConfirm.OnButtonClickedListener? = null)
}
