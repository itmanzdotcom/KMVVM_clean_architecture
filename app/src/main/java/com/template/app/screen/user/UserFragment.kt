package com.template.app.screen.user

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.template.app.R
import com.template.app.base.BaseFragment
import com.template.app.base.recyclerView.OnItemClickListener
import com.template.app.data.model.User
import com.template.app.util.extension.isNull
import com.template.app.util.extension.notNull
import com.template.app.util.liveData.Status
import com.template.app.util.liveData.autoCleared
import com.template.app.widget.superRecyclerView.SuperRecyclerView
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserFragment : BaseFragment<UserViewModel>(), OnItemClickListener<User>,
        SuperRecyclerView.LoadDataListener {

    override val viewModelx: UserViewModel by viewModel()
    private var adapter by autoCleared<UserAdapter>()

    override val layoutID: Int
        get() = R.layout.fragment_user


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = UserAdapter(
                context = context as AppCompatActivity).apply {
            registerItemClickListener(this@UserFragment)
        }
    }

    override fun initialize() {
        recyclerView.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setLoadDataListener(this@UserFragment)
        }

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModelx.searchUser(Status.REFRESH_DATA)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewModelx.initRxSearch(editText = edtSearch)

        viewModelx.repoList.value?.data.isNull {
            viewModelx.query.value = "cat"
            viewModelx.searchUser(Status.LOADING)
        }
    }

    override fun onSubscribeObserver() {
        viewModelx.repoList.observe(this, Observer { resource ->
            val data = resource.data
            when (resource.status) {
                Status.SUCCESS -> {
                    dialogManager.hideLoading()
                    data.notNull { adapter.updateData(it) }
                }
                Status.LOADING -> {
                    dialogManager.hideLoading()
                    data.notNull { adapter.updateData(it) }
                }
                Status.SEARCH_DATA -> {
                    data.notNull {
                        recyclerView.refreshAdapter(newSize = it.size)
                        adapter.updateData(it)
                    }
                }
                Status.REFRESH_DATA -> {
                    data.notNull {
                        recyclerView.stopRefreshData()
                        recyclerView.refreshAdapter(newSize = it.size)
                        adapter.updateData(it)
                    }
                }
                Status.LOAD_MORE -> {
                    data.notNull {
                        recyclerView.stopLoadMore()
                        adapter.updateData(it)
                    }
                }
                else -> {
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        recyclerView.stopAllStatusLoadData()
    }

    override fun onItemViewClick(item: User, position: Int) {
        item.isFavorite = !item.isFavorite
        viewModelx.pressFavorite(item)
    }

    override fun onLoadMore(page: Int) {
        viewModelx.searchUser(Status.LOAD_MORE, page = page)
    }

    override fun onRefreshData() {
        viewModelx.searchUser(Status.REFRESH_DATA)
    }

    companion object {
        fun newInstance() = UserFragment()
        const val TAG = "UserFragment"
    }

}
