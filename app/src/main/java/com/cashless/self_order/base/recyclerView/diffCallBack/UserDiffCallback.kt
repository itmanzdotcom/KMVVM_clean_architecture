package com.cashless.self_order.base.recyclerView.diffCallBack

import androidx.recyclerview.widget.DiffUtil
import com.cashless.self_order.data.model.User
import javax.annotation.Nullable

class UserDiffCallback
(@Nullable private var olds: List<User>, @Nullable private var news: List<User>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return olds.size
    }

    override fun getNewListSize(): Int {
        return news.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: User? = olds[oldItemPosition]
        val newItem: User? = news[newItemPosition]
        return oldItem?.id == newItem?.id && oldItem?.name.equals(newItem?.name)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: User? = olds[oldItemPosition]
        val newItem: User? = news[newItemPosition]
        return oldItem == newItem
    }
}