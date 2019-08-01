package com.cashless.self_order.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ---------------------------
 * Created by [COFFEE CODE] on 1/3/19
 * Screen Name: Common
 * TODO: <Add a class header comment!>
 * ---------------------------
 */
class SpacesItemDecoration(private var space: Int = 0) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space
    }
}