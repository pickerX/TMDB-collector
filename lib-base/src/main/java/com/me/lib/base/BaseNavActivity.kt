package com.me.lib.base

import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.me.lib.base.utils.setupWithNavController

abstract class BaseNavActivity<T : ViewDataBinding> : BaseActivity<T>() {

    private var currentNavController: LiveData<NavController>? = null

    /**
     * 设置顶级目的地
     */
    protected fun setupNavController(
        navView: BottomNavigationView,
        @IdRes id: Int,
        targets: () -> List<Int>
    ) {
        val navGraphIds = targets.invoke()
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = navView.setupWithNavController(
            navGraphIds, supportFragmentManager, id, intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    protected open fun onNavigationFabClick() {}

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}