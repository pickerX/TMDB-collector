package com.me.lib.ui.recycler

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @author: pickerx
 * @date:2020/11/16 6:20 PM
 */
open class ViewHolder<Binding : ViewDataBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val binding = DataBindingUtil.bind<Binding>(itemView)

}
