package com.me.lib.ui.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.me.lib.ui.recycler.ItemViewCreator
import com.me.lib.ui.recycler.ViewHolder

class MuxAdapter : RecyclerView.Adapter<ViewHolder<*>>() {

    private val viewsArray = mutableListOf<ItemViewCreator<*>>()

    fun addItem(item: ItemViewCreator<*>) {
        viewsArray.add(item)
    }

    override fun getItemViewType(position: Int): Int {
        return viewsArray[position].getItemViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<*> {
        val holder = getItemViewCreator(viewType)
        val itemView = LayoutInflater.from(parent.context).inflate(holder.layout(), parent, false)
        return holder.newViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder<*>, position: Int) {
        val type = getItemViewType(position)
        val h = getItemViewCreator(type)
        h.onBindViewHolder(holder, position)
    }

    private fun getItemViewCreator(type: Int): ItemViewCreator<*> {
        var index = -1
        viewsArray.forEachIndexed { i, h ->
            if (h.getItemViewType() == type) {
                index = i
                return@forEachIndexed
            }
        }
        check(index != -1) { "unregister type:$type" }

        return viewsArray[index]
    }

    override fun getItemCount(): Int = viewsArray.size
}