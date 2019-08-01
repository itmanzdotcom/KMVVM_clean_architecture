package com.template.app.base.recyclerView

import com.template.app.util.Constants.POSITION_DEFAULT

/**
 * OnItemClickListener
 *
 * @param <T> Data from item click
</T> */

interface OnItemClickListener<T> {
  fun onItemViewClick(item: T, position: Int = POSITION_DEFAULT)
}