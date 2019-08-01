package com.template.app.screen.home

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.template.app.R
import com.template.app.base.BaseActivity
import com.template.app.screen.blank.BlankFragment
import com.template.app.screen.user.UserFragment
import com.template.app.screen.userFavorite.UserFavoriteFragment
import com.template.app.util.extension.addFragmentToActivity
import com.template.app.util.extension.switchFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity<HomeActivityViewModel>() {

    override val viewModelx: HomeActivityViewModel by viewModel()
    private lateinit var currentFragment: Fragment
    private var userFragment = UserFragment.newInstance()
    private val blankFragment: BlankFragment by lazy { BlankFragment.newInstance() }
    private val userFavoriteFragment: UserFavoriteFragment by lazy { UserFavoriteFragment.newInstance() }

    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private var isDoubleTapBack = false

    override val layoutID: Int
        get() = R.layout.activity_home

    override fun onBackPressed() {
        if (viewModelx.currentTab != TAB1) {
            bottomNav.selectedItemId = R.id.tab1
            switchTab(TAB1, userFragment)
            return
        }
        if (isDoubleTapBack) {
            finish()
            return
        }
        isDoubleTapBack = true
        Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
        handler?.postDelayed(runnable, DELAY_TIME_TWO_TAP_BACK_BUTTON.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(runnable)
    }

    override fun initialize() {
        val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab1 -> switchTab(
                        TAB1, userFragment)
                R.id.tab2 -> switchTab(
                        TAB2, blankFragment)
                R.id.tab3 -> switchTab(
                        TAB3, userFragment)
                R.id.tab4 -> switchTab(
                        TAB4, userFavoriteFragment)
                R.id.tab5 -> switchTab(
                        TAB5, userFragment)
            }
            return@OnNavigationItemSelectedListener true
        }
        bottomNav.selectedItemId = R.id.tab1
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        handler = Handler()
        runnable = Runnable { isDoubleTapBack = false }

        addFragmentToActivity(R.id.mainContainer, userFragment)
        currentFragment = userFragment
    }

    override fun onSubscribeObserver() {
    }

    private fun switchTab(currentTab: Int, fragment: Fragment) {
        if (viewModelx.currentTab == currentTab) {
            return
        }
        viewModelx.currentTab = currentTab
        switchFragment(R.id.mainContainer, currentFragment = currentFragment,
                newFragment = fragment)
        currentFragment = fragment
    }

    companion object {
        fun getInstance(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        const val DELAY_TIME_TWO_TAP_BACK_BUTTON = 2000

        const val TAB1 = 0
        const val TAB2 = 1
        const val TAB3 = 2
        const val TAB4 = 3
        const val TAB5 = 4
    }
}
