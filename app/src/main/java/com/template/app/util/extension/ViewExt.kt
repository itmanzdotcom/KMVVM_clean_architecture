package com.template.app.util.extension

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.template.app.util.GlideApp
import com.template.app.util.LogUtils

fun View.show(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.gone(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun Context.showToast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

@SuppressLint("SetJavaScriptEnabled")
fun WebView.loadWebViewUrl(url: String?, progressBar: ProgressBar?) {
    if (url.isNullOrEmpty()) return
    if (progressBar == null) {
        webViewClient = WebViewClient()
    } else {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }
    }
    with(settings) {
        javaScriptEnabled = true
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }
    loadUrl(url)
    LogUtils.d("loadWebViewUrl", url)
}

fun ImageView.loadImageUrl(url: String?) {
    url.notNull {
        GlideApp.with(this.context).load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontTransform()
                .into(this)
    }
}


fun View.OnClickListener.listenToViews(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

fun View.setIsSelected(isSelect: Boolean = true) {
    this.isSelected = isSelect
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnVeryLongClickListener(timeDelay: Long = 3000, listener: () -> Unit) {
    setOnTouchListener(object : View.OnTouchListener {

        private val handler = Handler(Looper.getMainLooper())

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed({
                    listener.invoke()
                }, timeDelay)
            } else if (event?.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            return true
        }
    })
}