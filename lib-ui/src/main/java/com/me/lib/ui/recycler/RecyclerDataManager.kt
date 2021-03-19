package com.me.lib.ui.recycler

interface RecyclerDataManager<E> {

    val dataSet: MutableList<E>

    fun updateAll(data: List<E>) {
        this.dataSet.clear()
        this.dataSet.addAll(data)
    }

    fun get(position: Int) = dataSet[position]

    fun notifyDataSetChanged()
}