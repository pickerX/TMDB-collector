package io.tmdb.collector.room.conveter

import androidx.room.TypeConverter
import com.alibaba.fastjson.JSONArray

/**
 *
 * @author: pickerx
 * @date:2020/12/2 11:00 AM
 */
open class IntListConverter {

    @TypeConverter
    fun fromString(value: String): List<Int>? = JSONArray.parseArray(value, Int::class.java)

    @TypeConverter
    fun fromList(list: List<Int>): String = JSONArray.toJSONString(list)
}