package com.cashless.self_order.screen.userFavorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cashless.self_order.R
import com.cashless.self_order.base.BaseFragment
import com.cashless.self_order.base.recyclerView.OnItemClickListener
import com.cashless.self_order.data.model.User
import com.cashless.self_order.screen.user.UserAdapter
import com.cashless.self_order.util.extension.notNull
import com.cashless.self_order.util.liveData.Status
import com.cashless.self_order.util.liveData.autoCleared
import kotlinx.android.synthetic.main.fragment_user_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFavoriteFragment : BaseFragment<UserFavoriteViewModel>(), OnItemClickListener<User> {

    override val viewModelx: UserFavoriteViewModel by viewModel()
    private var adapter by autoCleared<UserAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user_favorite


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = UserAdapter(
                context = context as AppCompatActivity).apply {
            registerItemClickListener(this@UserFavoriteFragment)
        }
    }

    override fun initialize() {
        recyclerView.adapter = adapter
    }

    override fun onSubscribeObserver() {
        viewModelx.repoList.observe(this, Observer { resource ->
            if (resource.status == Status.LOADING) {
                dialogManager.hideLoading()
                resource.data.notNull {
                    adapter.updateData(it)
                }
            }
        })
    }

    override fun onItemViewClick(item: User, position: Int) {
        item.isFavorite = !item.isFavorite
        viewModelx.pressFavorite(item)
    }

    companion object {
        fun newInstance() = UserFavoriteFragment()
        const val TAG = "UserFavoriteFragment"
    }

}
