package com.cashless.self_order.di

import com.cashless.self_order.util.extension.withScheduler
import com.cashless.self_order.util.rxAndroid.BaseSchedulerProvider
import io.reactivex.subjects.PublishSubject

/**
 * ---------------------------
 * Created by [PIKA] on 1/26/19
 * Screen Name: Authenticate
 * TODO: <Add a class header comment!>
 * ---------------------------
 */
class UserAuthManager(private val baseScheduleProvider: BaseSchedulerProvider) {

    private val onUserLoginSuccessListener = PublishSubject.create<Int>()

    private val onUserLogoutSuccessListener = PublishSubject.create<Int>()

    private val onSettingPaymentMethodSuccessListener = PublishSubject.create<Boolean>()

    fun sendUserLoginSuccess(loginStartFrom: Int) = onUserLoginSuccessListener.onNext(
            loginStartFrom)

    fun sendUserLogOutSuccess() = onUserLogoutSuccessListener.onNext(1)

    fun onUserLoginSuccessListener() = onUserLoginSuccessListener.withScheduler(
            baseScheduleProvider)

    fun onUserLogoutSuccessListener() = onUserLogoutSuccessListener.withScheduler(
            baseScheduleProvider)

    fun sendSettingPaymentMethodSuccess(
            isSuccess: Boolean) = onSettingPaymentMethodSuccessListener.onNext(isSuccess)

    fun onSettingPaymentMethodSuccessListener() = onSettingPaymentMethodSuccessListener.withScheduler(
            baseScheduleProvider)

}