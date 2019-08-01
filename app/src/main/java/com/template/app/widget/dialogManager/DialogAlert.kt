package com.template.app.widget.dialogManager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.template.app.R
import com.template.app.util.RxView
import com.template.app.util.StringUtils
import com.template.app.util.extension.notNull
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_alert.*

/**
 * ---------------------------
 * Created by [Android Lovers] on 1/9/19
 * Screen Name: Common
 * TODO: <Add a class header comment!>
 * ---------------------------
 */
class DialogAlert : DialogFragment() {
    private lateinit var compositeDisposable: CompositeDisposable
    var listener: OnButtonClickedListener? = null
    private var title: String? = ""
    private var message: String? = ""
    private var titleBtn: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        compositeDisposable = CompositeDisposable()

        arguments?.let {
            title = it.getString(
                    TITLE_EXTRA)
            message = it.getString(
                    MESSAGE_EXTRA)
            titleBtn = it.getString(
                    TITLE_BUTTON_EXTRA)
        }

        return inflater.inflate(R.layout.dialog_alert, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        tvTitle.text = title
        tvContent.text = message
        if (StringUtils.isNotEmpty(titleBtn)) {
            btnPositive.text = titleBtn
        }
        if (StringUtils.isBlank(title)) {
            tvTitle.visibility = View.GONE
        }

        val actionDisposable = RxView.clicks(btnPositive,
                false)
                .subscribe {
                    dismiss()
                    listener.notNull { action ->
                        action.onPositiveClicked()
                    }
                }
        compositeDisposable.add(actionDisposable)
    }

    override fun onDestroy() {
        this.compositeDisposable.clear()
        super.onDestroy()
    }

    companion object {
        private const val TITLE_EXTRA = "TITLE_EXTRA"
        private const val MESSAGE_EXTRA = "MESSAGE_EXTRA"
        private const val TITLE_BUTTON_EXTRA = "TITLE_BUTTON_EXTRA"

        fun newInstance(title: String, message: String, titleBtn: String,
                listener: OnButtonClickedListener?): DialogAlert {
            return DialogAlert().apply {
                arguments = Bundle().apply {
                    putString(
                            TITLE_EXTRA, title)
                    putString(
                            MESSAGE_EXTRA, message)
                    putString(
                            TITLE_BUTTON_EXTRA, titleBtn)
                }
                this.listener = listener
            }
        }

        interface OnButtonClickedListener {
            fun onPositiveClicked()
        }
    }
}