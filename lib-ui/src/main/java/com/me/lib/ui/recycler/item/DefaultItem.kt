package com.me.lib.ui.recycler.item

import com.me.lib.ui.OnItemClickListener
import com.me.lib.ui.recycler.ItemViewCreator

/**
 *
 * @author: pickerx
 * @date:2020/11/16 4:37 PM
 */
abstract class DefaultItem<T> : ItemViewCreator<T> {

    open val dataSet: MutableList<T> = mutableListOf()

    // for override
    override var onItemClickListener: OnItemClickListener? = null

    fun update(data: List<T>) {
        dataSet.clear()
        dataSet.addAll(data)
    }

    override fun getItemViewType(): Int = hashCode()

    override fun getItem(position: Int): T? = if (dataSet.isNotEmpty()) dataSet[position] else null

    override fun getItemId(position: Int): Long = position.toLong()
}