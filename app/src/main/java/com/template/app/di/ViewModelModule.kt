package com.template.app.di

import com.template.app.screen.home.HomeActivityViewModel
import com.template.app.screen.splash.SplashActivityViewModel
import com.template.app.screen.user.UserViewModel
import com.template.app.screen.userFavorite.UserFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * --------------------
 * Created by [Android Lovers] on 6/17/2019.
 * Screen name:
 * --------------------
 */
val viewModelModule = module {
    viewModel { HomeActivityViewModel() }
    viewModel { SplashActivityViewModel() }
    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { UserFavoriteViewModel(get()) }
}