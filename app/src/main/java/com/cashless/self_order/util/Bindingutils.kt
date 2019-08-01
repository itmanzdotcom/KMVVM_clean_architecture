package com.cashless.self_order.util

import android.graphics.Paint
import android.graphics.PorterDuff
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.cashless.self_order.R
import com.cashless.self_order.util.extension.notNull
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout

object BindingUtils {

    @JvmStatic
    @BindingAdapter("currentPage")
    fun setCurrentViewPager(viewPager: ViewPager, currentPage: Int) {
        viewPager.currentItem = currentPage
    }

    @JvmStatic
    @BindingAdapter("viewPagerAdapter")
    fun setViewPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter) {
        viewPager.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("viewPager")
    fun setViewPagerTabs(tabLayout: TabLayout, viewPager: ViewPager) {
        tabLayout.setupWithViewPager(viewPager, true)
    }

    @JvmStatic
    @BindingAdapter("hasFixedSize")
    fun setHasFixedSize(view: RecyclerView, isFixed: Boolean) {
        view.setHasFixedSize(isFixed)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String) {
        url.notNull {
            GlideApp.with(imageView.context).load(it).transition(
                    DrawableTransitionOptions.withCrossFade()).into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrlWithLoading")
    fun setImageUrlWithLoading(imageView: ImageView, url: String?) {
        val loading = CircularProgressDrawable(imageView.context)
        loading.strokeWidth = 10f
        loading.centerRadius = 50f
        loading.setColorFilter(ContextCompat.getColor(imageView.context, R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN)
        loading.start()
        url.notNull {
            GlideApp.with(imageView.context)
                    .load(it)
                    .placeholder(loading)
                    .error(R.drawable.ic_favorite)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
            return
        }

        imageView.setImageResource(R.drawable.ic_favorite)
    }

    @JvmStatic
    @BindingAdapter("imageUrlNormal")
    fun imageUrlNormal(imageView: ImageView, url: String?) {
        url.notNull {
            GlideApp.with(imageView.context)
                    .load(it)
                    .placeholder(R.drawable.ic_favorite)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("circleImageUrl")
    fun setCircleImageUrl(imageView: ImageView, url: String?) {
        url.notNull {
            Glide.with(imageView.context)
                    .load(it)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("imageResourceID")
    fun setImageResourceID(imageView: ImageView, resourceID: Int) {
        if (resourceID == 0) return
        imageView.setImageResource(resourceID)
    }

    @JvmStatic
    @BindingAdapter("colorResourceID")
    fun setColorResourceID(view: View, resourceID: Int) {
        if (resourceID == 0) return
        view.setBackgroundResource(resourceID)
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("invisible")
    fun setInvisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("isRefreshing")
    fun setSwipeRefreshing(view: SwipeRefreshLayout, isRefreshing: Boolean) {
        view.isRefreshing = isRefreshing
    }


    @JvmStatic
    @BindingAdapter("parseHtmlToText")
    fun parseHtmlToText(textView: TextView, bodyData: String) {
        if (TextUtils.isEmpty(bodyData)) {
            return
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.text = Html.fromHtml(bodyData, Html.FROM_HTML_MODE_LEGACY)
        } else {
            textView.text = Html.fromHtml(bodyData)
        }
    }

    @JvmStatic
    @BindingAdapter("setTextUnderLine")
    fun setTextUnderLine(textView: TextView, content: String) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        textView.text = content
    }

    @JvmStatic
    @BindingAdapter("setSpinnerList")
    fun setSpinnerList(spinner: Spinner, adapter: BaseAdapter) {
        spinner.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("onItemSelectedListener", "setSelection")
    fun Spinner.onItemSelectedListener(listener: AdapterView.OnItemSelectedListener,
            selectedPos: Int) {
        onItemSelectedListener = listener
        setSelection(selectedPos)
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("errorTextInput")
    fun setErrorTextInput(view: TextInputLayout, error: String) {
        view.error = error
        view.isErrorEnabled = !error.isEmpty()
    }

    @JvmStatic
    @BindingAdapter("setPriceFormat")
    fun setPriceFormat(textView: TextView, price: Long) {
        textView.text = StringUtils.formatMoney(price)
    }

}