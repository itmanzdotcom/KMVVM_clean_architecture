package com.cashless.self_order.util

import android.content.Context
import android.net.ConnectivityManager
import com.cashless.self_order.MainApplication


class InternetManager {

    companion object {
        fun isConnected(): Boolean {
            val cm = MainApplication.sInstance.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return cm?.activeNetworkInfo != null
        }
    }

}