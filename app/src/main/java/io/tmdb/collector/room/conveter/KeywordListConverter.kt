package io.tmdb.collector.room.conveter

import androidx.room.TypeConverter
import com.alibaba.fastjson.JSONArray
import io.tmdb.collector.data.Keyword

/**
 *
 * @author: pickerx
 * @date:2020/12/2 11:10 AM
 */
open class KeywordListConverter {
    @TypeConverter
    fun fromString(value: String): List<Keyword>? = JSONArray.parseArray(value, Keyword::class.java)

    @TypeConverter
    fun fromList(list: List<Keyword>): String = JSONArray.toJSONString(list)
}
