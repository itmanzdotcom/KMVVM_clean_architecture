package com.cashless.self_order.di

import com.cashless.self_order.screen.home.HomeActivityViewModel
import com.cashless.self_order.screen.splash.SplashActivityViewModel
import com.cashless.self_order.screen.user.UserViewModel
import com.cashless.self_order.screen.userFavorite.UserFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * --------------------
 * Created by ThuanPx on 6/17/2019.
 * Screen name:
 * --------------------
 */
val viewModelModule = module {
    viewModel { HomeActivityViewModel() }
    viewModel { SplashActivityViewModel() }
    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { UserFavoriteViewModel(get()) }
}