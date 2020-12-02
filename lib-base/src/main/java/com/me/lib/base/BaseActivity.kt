package com.me.lib.base

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.me.lib.base.utils.requestFullScreen

/**
 * base activity for others
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        onPreCreate()

        super.onCreate(savedInstanceState)

        if (isRequiredFullScreen())
            requestFullScreen(window)
        else
            overridePendingTransition(R.anim.in_push_right_to_left, R.anim.out_push_right_to_left)

        binding = DataBindingUtil.setContentView(this, layout())

        initListeners()

        onViewsReady()

        if (savedInstanceState != null) onSavedInstanceReady(savedInstanceState)
    }

    protected fun hideBack() {
        supportActionBar?.let {
            val toolbar = it as Toolbar
            toolbar.navigationIcon?.setVisible(false, false)
        }
    }

    protected fun hideActionBar(hide: Boolean = true) {
        if (hide) {
            actionBar?.hide()
            supportActionBar?.hide()
        } else {
            actionBar?.show()
            supportActionBar?.show()
        }
    }


    /**
     * need request full screen or not
     */
    protected open fun isRequiredFullScreen(): Boolean = false

    /**
     * get application
     */
    protected open fun myApplication(): Application = application

    /**
     * call before super.onCreate()
     * you can do inject or something else
     */
    protected open fun onPreCreate(): Boolean = true

    protected open fun onSavedInstanceReady(savedInstanceState: Bundle) {}

    /**
     * init listeners
     */
    protected open fun initListeners() {}

    /**
     * activity layout resource
     */
    protected abstract fun layout(): Int

    /**
     * call after setContentView
     */
    protected abstract fun onViewsReady()

}