package com.me.lib.ui.recycler

import android.view.View
import com.me.lib.ui.OnItemClickListener

interface ItemViewCreator<T> {

    var onItemClickListener: OnItemClickListener?

    fun layout(): Int

    fun getItemViewType(): Int

    fun newViewHolder(itemView: View): ViewHolder<*>

    fun onBindViewHolder(holder: ViewHolder<*>, position: Int)

    fun getItem(position: Int): T?

    fun getItemId(position: Int): Long
}