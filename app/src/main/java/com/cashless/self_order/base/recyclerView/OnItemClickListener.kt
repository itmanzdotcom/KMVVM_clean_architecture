package com.cashless.self_order.base.recyclerView

import com.cashless.self_order.util.Constants.POSITION_DEFAULT

/**
 * OnItemClickListener
 *
 * @param <T> Data from item click
</T> */

interface OnItemClickListener<T> {
  fun onItemViewClick(item: T, position: Int = POSITION_DEFAULT)
}