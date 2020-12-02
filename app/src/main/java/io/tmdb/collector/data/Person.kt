package io.tmdb.collector.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import io.tmdb.collector.data.http.PersonDetail
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:36 AM
 */

@Parcelize
@Entity(tableName = "People", primaryKeys = ["id"])
data class Person(
    var page: Int,
    @Embedded var personDetail: PersonDetail? = null,
    val profile_path: String?,
    val adult: Boolean,
    val id: Int,
    val name: String,
    val popularity: Float
) : Parcelable
