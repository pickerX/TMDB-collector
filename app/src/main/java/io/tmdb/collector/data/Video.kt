package io.tmdb.collector.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:40 AM
 */
@Parcelize
data class Video(
    val id: String,
    val name: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
) : Parcelable
