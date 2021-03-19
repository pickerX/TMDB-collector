package com.me.lib.ui

import android.view.View

/** Type helper used for the callback triggered once our view has been bound */
typealias OnBindViewHolder<T> = (view: View, data: T, position: Int) -> Unit

typealias OnVHBinding<T, E> = (binding: E, dataSet: MutableList<T>, position: Int) -> Unit

/** Type helper used for the callback triggered once our view has been bound */
typealias OnItemClickListener = (view: View, position: Int) -> Unit