package com.template.app.widget.dialogManager

import com.template.app.util.ToastType

interface DialogManager {

  fun showLoading()

  fun showProcessing()

  fun hideLoading()

  fun onRelease()

  fun showDialogType(@ToastType.Toast toastType: String, message: String)

  fun showToastSuccess(message: String)

  fun showAlertDialog(title: String, message: String, titleButton: String,
          listener: DialogAlert.Companion.OnButtonClickedListener?)

  fun showConfirmDialog(title: String?, message: String?,
          titleButtonPositive: String, titleButtonNegative: String,
          listener: DialogConfirm.OnButtonClickedListener?)
}