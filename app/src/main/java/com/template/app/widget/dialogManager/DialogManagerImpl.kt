package com.template.app.widget.dialogManager

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.template.app.MainApplication
import com.template.app.R
import com.template.app.util.Constants
import com.template.app.util.StringUtils
import com.template.app.util.ToastType
import com.template.app.util.extension.notNull
import java.lang.ref.WeakReference

class DialogManagerImpl(ctx: Context?) : DialogManager {

  private var context: WeakReference<Context?>? = null
  private var progressDialog: ProgressDialog? = null
  private var handler = Handler(Looper.myLooper())
  private var viewToast: View? = null
  private var toast: Toast? = null

  init {
    context = WeakReference(ctx).apply {
      progressDialog = ProgressDialog(this.get()!!)
    }
  }

  override fun showLoading() {
    progressDialog?.show()
  }

  override fun showProcessing() {
    progressDialog?.show()
  }

  override fun hideLoading() {
    progressDialog?.dismiss()
  }

  override fun onRelease() {
    progressDialog = null
    com.template.app.util.LogUtils.d(TAG, Constants.RELEASED)
  }

  override fun showDialogType(toastType: String, message: String) {
    if (StringUtils.isBlank(message)) {
      return
    }

    if (Constants.HTTP_UNAUTHORIZED.toString() == message) {
      context?.get().notNull {
        MainApplication.sInstance.onLogout()
      }

    }

    when (toastType) {
      ToastType.SUCCESS -> showToastSuccess(message)
      ToastType.INFO -> Toast.makeText(context?.get(), message, Toast.LENGTH_SHORT).show()
      ToastType.WARNING -> Toast.makeText(context?.get(), message, Toast.LENGTH_SHORT).show()
//            ToastType.ERROR -> showAlertDialog(context?.get() as AppCompatActivity, "Notice",message, "Ok", null)
    }
  }

  override fun showToastSuccess(message: String) {
    if (viewToast == null) {
      val inflater = LayoutInflater.from(context?.get())
      viewToast = inflater.inflate(R.layout.view_toast_success,
              (context?.get() as Activity).findViewById(R.id.layout_toast_success))

      toast = Toast(context?.get())
      toast?.setGravity(Gravity.CENTER, 0, 0)
      toast?.duration = Toast.LENGTH_SHORT
      toast?.view = viewToast
    }
    val textView = viewToast?.findViewById<AppCompatTextView>(R.id.titleToast)
    textView?.text = message
    toast?.show()
  }

  override fun showAlertDialog(title: String, message: String, titleButton: String,
          listener: DialogAlert.Companion.OnButtonClickedListener?) {
    val dialog = DialogAlert.newInstance(title, message, titleButton, listener)
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  override fun showConfirmDialog(title: String?, message: String?,
          titleButtonPositive: String, titleButtonNegative: String,
          listener: DialogConfirm.OnButtonClickedListener?) {
    val dialog = DialogConfirm.newInstance(title, message, titleButtonPositive,
            titleButtonNegative, listener)
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  companion object {
    const val TAG = "DialogManagerImpl"
  }

}