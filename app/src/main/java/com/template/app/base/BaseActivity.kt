package com.template.app.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.template.app.MainApplication
import com.template.app.R
import com.template.app.data.source.remote.api.error.RetrofitException
import com.template.app.screen.home.HomeActivity
import com.template.app.util.Constants
import com.template.app.util.extension.notNull
import com.template.app.widget.dialogManager.DialogAlert
import com.template.app.widget.dialogManager.DialogConfirm
import com.template.app.widget.dialogManager.DialogManager
import com.template.app.widget.dialogManager.DialogManagerImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.HttpURLConnection

@SuppressLint("Registered")
abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(), MainApplication.ReLoginListener, BaseView {

    protected abstract val layoutID: Int
    protected abstract val viewModelx: VM

    lateinit var dialogManager: DialogManager
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutID)
        dialogManager = DialogManagerImpl(this)
        initialize()
        onSubscribeObserver()
    }

    override fun onStart() {
        super.onStart()
        MainApplication.sInstance.setCurrentClass(javaClass)
        MainApplication.sInstance.registerReLoginListener(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        dialogManager.onRelease()
        super.onDestroy()
    }

    override fun showLoading() {
        dialogManager.showLoading()
    }

    override fun hideLoading() {
        dialogManager.hideLoading()
    }

    override fun showToastSuccess(message: String) {
        dialogManager.showToastSuccess(message)
    }

    override fun showDialogType(toastType: String, message: String) {
        dialogManager.showDialogType(toastType, message)
    }

    override fun showAlertDialog(title: String, message: String,
            titleButton: String, listener: DialogAlert.Companion.OnButtonClickedListener?) {
        dialogManager.showAlertDialog(title, message, titleButton, listener)
    }

    override fun showConfirmDialog(title: String?, message: String?,
            titleButtonPositive: String, titleButtonNegative: String,
            listener: DialogConfirm.OnButtonClickedListener?) {
        dialogManager.showConfirmDialog(title, message, titleButtonPositive, titleButtonNegative,
                listener)
    }

    override fun handleApiError(apiError: Throwable) {
        if (apiError is RetrofitException) {
            if (apiError.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                apiError.getMsgError().notNull {
                    showAlertDialog(message = it, listener = object : DialogAlert.Companion.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            reLogin()
                        }
                    })
                }
                return
            }
            if (apiError.getErrorCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                apiError.getMsgError().notNull {
                    showAlertDialog(message = it, listener = object : DialogAlert.Companion.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            MainApplication.sInstance.onLogout()
                            val intent = Intent(application, HomeActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        }
                    })
                }
                return
            }
            apiError.getMsgError().notNull {
                showAlertDialog(message = it)
            }
        }
    }

    override fun onError(@StringRes resId: Int) {
    }

    override fun launchDisposable(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    override fun onReLogin() {
        showAlertDialog(message = getString(R.string.err_acc_has_changed_plz_login_again),
                listener = object : DialogAlert.Companion.OnButtonClickedListener {
                    override fun onPositiveClicked() {
                        reLogin()
                    }
                })
    }

    private fun reLogin() {
        MainApplication.sInstance.onLogout()
        val intent = Intent(this@BaseActivity, HomeActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.Bundles.RELOGIN_EXTRA,
                Constants.Bundles.RELOGIN_EXTRA)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    protected abstract fun initialize()

    protected abstract fun onSubscribeObserver()

}