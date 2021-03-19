package io.tmdb.collector.data

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.alibaba.fastjson.annotation.JSONCreator
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:36 AM
 */
@Parcelize
@Entity(tableName = "People", primaryKeys = ["id"])
data class Person(
    var id: Int,
    var profile_path: String?,
    var adult: Boolean,
    var name: String,
    var popularity: Float,
    @Embedded var personDetail: PersonDetail? = null
) : Parcelable {
    @JSONCreator
    constructor() : this(0, null, false, "", 0F)
}
