package com.template.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import com.template.app.R
import com.template.app.util.extension.notNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseDialogFragment<VM: ViewModel> : DialogFragment() {

    val compositeDisposable = CompositeDisposable()
    protected abstract val viewModelx: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return getLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        onSubscribeObserver()
    }

    override fun onStart() {
        super.onStart()
        val dlg = dialog
        dlg.notNull {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dlg?.window?.setLayout(width, height)
        }
    }

    override fun onDetach() {
        compositeDisposable.clear()
        super.onDetach()
    }

    fun launchDisposable(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    abstract fun getLayout(): View
    abstract fun initialize()
    abstract fun onSubscribeObserver()
}