package com.template.app.screen.user

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.template.app.R
import com.template.app.base.recyclerView.BaseLoadMoreAdapter
import com.template.app.data.model.User
import com.template.app.util.extension.inflate
import com.template.app.util.extension.listen
import com.template.app.util.extension.loadImageUrl
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(context: Context) : BaseLoadMoreAdapter<User>(context) {


    @NonNull
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.inflate(R.layout.item_user)
        return ItemViewHolder(itemView).listen { position, _ ->
            getItem(position)?.let { itemClickListener?.onItemViewClick(it, position) }
        }
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindData(getItem(position))
    }

    override fun getItemViewTypeLM(position: Int): Int = 0

    companion object {
        class ItemViewHolder(view: View) : RecyclerView.ViewHolder(
                view) {

            fun bindData(user: User?) {
                user?.let {
                    with(itemView) {
                        textView.text = user.name
                        imageView.loadImageUrl(user.owner?.avatarUrl)
                    }
                }
            }
        }
    }
}