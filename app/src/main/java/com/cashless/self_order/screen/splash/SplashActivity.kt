package com.cashless.self_order.screen.splash

import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import com.cashless.self_order.R
import com.cashless.self_order.base.BaseActivity
import com.cashless.self_order.data.source.repositories.UserRepository
import com.cashless.self_order.screen.home.HomeActivity
import com.cashless.self_order.util.extension.startActivityAtRoot
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ---------------------------
 * Created by [TOKUDA J.] on 2019-06-03
 * Screen Name:
 * TODO: <Add a class header comment!>
 * ---------------------------
 */
class SplashActivity : BaseActivity<SplashActivityViewModel>() {

    override val viewModelx: SplashActivityViewModel by viewModel()
    private val userRepository: UserRepository by inject()

    private var delayHandle: Handler = Handler()
    private var counterTimer: CountDownTimer? = null

    private val runnable = Runnable {
        startActivityAtRoot(this, HomeActivity::class.java, args = intent.extras)
    }

    override val layoutID: Int
        get() = R.layout.activity_splash


    override fun onSubscribeObserver() {
    }

    override fun initialize() {
        counterTimer = object : CountDownTimer(DELAY_TIME, 1000) {
            override fun onFinish() {
                startActivityAtRoot(this@SplashActivity, HomeActivity::class.java, args = intent.extras)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                //called every 1 sec coz countDownInterval = 1000 (1 sec)
            }
        }
        counterTimer?.start()
    }

    override fun onDestroy() {
        delayHandle.removeCallbacks(runnable)
        counterTimer?.cancel()
        super.onDestroy()
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
        super.onNewIntent(intent)
    }

    companion object {
        private const val DELAY_TIME: Long = 2000
    }
}