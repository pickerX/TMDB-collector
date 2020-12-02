package io.tmdb.collector.data.http

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:43 AM
 */

@Parcelize
data class PersonDetail(
    val birthday: String?,
    val known_for_department: String,
    val place_of_birth: String?,
    val also_known_as: List<String>?,
    val biography: String
) : Parcelable
