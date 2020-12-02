package com.me.lib.ui.recycler

/**
 *
 * @author: pickerx
 * @date:2020/11/16 4:37 PM
 */
abstract class DefaultItem<T>() : ItemViewCreator<T> {

    private val dataSet: MutableList<T> = mutableListOf()

    fun update(data: List<T>) {
        dataSet.clear()
        dataSet.addAll(data)
    }

    override fun getItemViewType(): Int = hashCode()

    override fun getItem(position: Int): T = dataSet[position]

    override fun getItemId(position: Int): Long = position.toLong()
}