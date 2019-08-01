package com.cashless.self_order.base.recyclerView

import androidx.databinding.ObservableBoolean

data class ItemLoadMoreViewModel(
    val visibleProgressBar: ObservableBoolean = ObservableBoolean(false))