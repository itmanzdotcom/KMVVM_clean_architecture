package com.template.app.screen.splash

import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import com.template.app.R
import com.template.app.base.BaseActivity
import com.template.app.data.source.repositories.UserRepository
import com.template.app.screen.home.HomeActivity
import com.template.app.util.extension.startActivityAtRoot
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * ---------------------------
 * Created by [Android Lovers] on 2019-06-03
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