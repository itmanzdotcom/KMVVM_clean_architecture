package com.cashless.self_order.screen.user

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cashless.self_order.R
import com.cashless.self_order.base.recyclerView.BaseLoadMoreAdapter
import com.cashless.self_order.base.recyclerView.OnItemClickListener
import com.cashless.self_order.base.recyclerView.diffCallBack.UserDiffCallback
import com.cashless.self_order.data.model.User
import com.cashless.self_order.databinding.ItemUserBinding

class UserAdapter(context: Context) : BaseLoadMoreAdapter<User>(context) {

    override fun getItemViewTypeLM(position: Int): Int {
        return 0
    }

    @NonNull
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
                DataBindingUtil.inflate<ItemUserBinding>(layoutInflater, R.layout.item_user, parent,
                        false)
        return ItemViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(getItem(position), position)
    }

    inner class ItemViewHolder(
            private val binding: ItemUserBinding,
            private val itemClickListener: OnItemClickListener<User>?,
            private val itemViewModel: ItemUserViewModel =
                    ItemUserViewModel(itemClickListener)) : RecyclerView.ViewHolder(
            binding.root) {

        init {
            binding.viewModel = itemViewModel
        }

        fun bind(user: User?, position: Int) {
            itemViewModel.position = position
            itemViewModel.setData(user)
            binding.executePendingBindings()
        }
    }

    fun updateData(newData: MutableList<User>) {
        handler.post {
            val callBack = UserDiffCallback(
                    dataList, newData)
            val diffResult = DiffUtil.calculateDiff(callBack)
            diffResult.dispatchUpdatesTo(this)
            dataList.clear()
            dataList.addAll(newData)
        }
    }


}