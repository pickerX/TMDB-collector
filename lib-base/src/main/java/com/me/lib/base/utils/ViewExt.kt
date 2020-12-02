package com.me.lib.base.utils

import android.app.Service
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat


fun View.showKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}

fun View.colorOf(@ColorRes color: Int): Int =
    ResourcesCompat.getColor(context.resources, color, null)


fun View.drawableOf(@DrawableRes drawable: Int): Drawable =
    ResourcesCompat.getDrawable(resources, drawable, null)!!

fun View.dimensOf(@DimenRes dimen: Int): Float = resources.getDimension(dimen)
