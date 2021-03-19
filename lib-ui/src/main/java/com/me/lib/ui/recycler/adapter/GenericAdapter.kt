package com.me.lib.ui.recycler.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.me.lib.ui.OnItemClickListener
import com.me.lib.ui.OnVHBinding
import com.me.lib.ui.recycler.ViewHolder

/**
 * Generic Adapter for RecyclerView
 * use when only one type itemView likes,
 *  1. simple list view
 *  2. view pager2
 *  3. grid list view
 *
 * @author: pickerx
 * @date:2021/3/1 2:16 PM
 */
open class GenericAdapter<T, E : ViewDataBinding>(@LayoutRes val layoutRes: Int) :
    RecyclerView.Adapter<ViewHolder<E>>() {
    // for override
    var onItemClickListener: OnItemClickListener? = null
    var onVHBinding: OnVHBinding<T, E>? = null

    private val dataSet: MutableList<T> = mutableListOf()

    fun bind(context: Context, view: RecyclerView) {
        view.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.adapter = this
    }

    fun updateAll(data: List<T>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<E> {
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder<E>, position: Int) {
        if (onBindVH(holder.binding, dataSet, position)) {
            onVHBinding?.invoke(holder.binding, dataSet, position)
        }
    }

    /**
     * bind view holder
     * @return true when extra bind data
     */
    open fun onBindVH(binding: E, dataSet: MutableList<T>, position: Int): Boolean = true

    override fun getItemCount(): Int = dataSet.size

}