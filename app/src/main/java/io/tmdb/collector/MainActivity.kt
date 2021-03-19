package io.tmdb.collector

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.me.lib.base.BaseNavActivity
import com.me.lib.base.utils.WindowExt
import dagger.hilt.android.AndroidEntryPoint
import io.tmdb.collector.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseNavActivity<ActivityMainBinding>() {

    override fun layout(): Int = R.layout.activity_main

    override fun onPreCreate(): Boolean {
//        WindowExt.setStatusBarColor(this)
        return super.onPreCreate()
    }

    override fun onViewsReady() {
        val navView: BottomNavigationView = binding.navView

        setupNavController(navView, R.id.nav_host_fragment) {
            listOf(
                R.navigation.navigation_home,
                R.navigation.navigation_dashboard,
                R.navigation.navigation_notifications
            )
        }
    }
}