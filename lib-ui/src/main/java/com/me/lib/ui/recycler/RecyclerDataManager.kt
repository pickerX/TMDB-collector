package com.me.lib.ui.recycler

interface RecyclerDataManager<E> {

    val data: MutableList<E>

    fun setData(data: List<E>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun get(position: Int) = data[position]

    fun notifyDataSetChanged()
}