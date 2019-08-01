package com.template.app.base.recyclerView

import androidx.databinding.ObservableBoolean

data class ItemLoadMoreViewModel(
    val visibleProgressBar: ObservableBoolean = ObservableBoolean(false))