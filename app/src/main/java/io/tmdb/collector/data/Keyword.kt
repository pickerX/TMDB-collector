package io.tmdb.collector.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:41 AM
 */
@Parcelize
data class Keyword(
    val id: Int,
    val name: String
) : Parcelable
