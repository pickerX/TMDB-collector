package io.tmdb.collector.data

import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONCreator
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author: pickerx
 * @date:2020/12/2 10:43 AM
 */
@Parcelize
data class PersonDetail(
    var birthday: String?,
    var known_for_department: String,
    var place_of_birth: String?,
    var also_known_as: List<String>?,
    var biography: String
) : Parcelable {

    @JSONCreator
    constructor() : this(null, "", null, null, "")
}
