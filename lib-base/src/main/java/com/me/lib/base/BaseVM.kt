package com.me.lib.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

/**
 *
 * @author: pickerx
 * @date:2020/11/24 3:39 PM
 */
open class BaseVM : ViewModel() {
    protected val apiContext = viewModelScope.coroutineContext + Dispatchers.IO
}