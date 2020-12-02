package io.tmdb.collector.room.conveter

import androidx.room.TypeConverter
import com.alibaba.fastjson.JSONArray

/**
 *
 * @author: pickerx
 * @date:2020/12/2 11:11 AM
 */
class StringListConverter {

    @TypeConverter
    fun fromString(value: String): List<String>? = JSONArray.parseArray(value, String::class.java)

    @TypeConverter
    fun fromList(list: List<String>): String = JSONArray.toJSONString(list)
}