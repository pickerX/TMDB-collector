package com.me.lib.ui.data

import android.content.Intent
import androidx.annotation.DrawableRes

/**
 * simple recycler view data
 * icon + title + description, with Intent
 */
data class TextItem(
    val title: String,
    val description: String = "",
    @DrawableRes val icon: Int = 0,
    val intent: Intent? = null
)