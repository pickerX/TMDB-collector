package com.me.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.me.lib.base.utils.autoCleared

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected var binding by autoCleared<T>()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preCreateView()

        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        onViewsReady()

        if (savedInstanceState != null) onSavedInstanceReady(savedInstanceState)
    }

    protected fun pop() {
        parentFragmentManager.popBackStack()
    }

    /**
     * @param toolbar clear ActionBar when null
     */
    protected fun setActionBar(toolbar: Toolbar?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    protected fun hideActionBar(hide: Boolean = true) {
        if (hide)
            (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        else
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    protected open fun initListeners() {}

    protected open fun preCreateView() {}

    protected open fun onSavedInstanceReady(savedInstanceState: Bundle) {}

    protected abstract fun layout(): Int

    protected abstract fun onViewsReady()
}