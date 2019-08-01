package com.cashless.self_order.screen.user

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.cashless.self_order.base.recyclerView.OnItemClickListener
import com.cashless.self_order.data.model.User
import com.cashless.self_order.util.Constants.POSITION_DEFAULT
import com.cashless.self_order.util.extension.notNull

class ItemUserViewModel(
    val itemClickListener: OnItemClickListener<User>? = null,
    var position: Int = POSITION_DEFAULT) : BaseObservable() {

  @Bindable
  var name = ""
  @Bindable
  var user: User? = null

  fun setData(data: User?) {
    data.notNull {
      user = it
      notifyPropertyChanged(BR.user)
      name = position.toString() + " : " + it.fullName
      notifyPropertyChanged(BR.name)
    }
  }

  fun onItemClicked(view: View) {
    itemClickListener.notNull { listener ->
      user.notNull {
        listener.onItemViewClick(it, position)
      }
    }
  }

}