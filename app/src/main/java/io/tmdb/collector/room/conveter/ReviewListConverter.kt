package io.tmdb.collector.room.conveter

import androidx.room.TypeConverter
import com.alibaba.fastjson.JSONArray
import io.tmdb.collector.data.Review

/**
 *
 * @author: pickerx
 * @date:2020/12/2 11:11 AM
 */
class ReviewListConverter {

    @TypeConverter
    fun fromString(value: String): List<Review>? = JSONArray.parseArray(value, Review::class.java)

    @TypeConverter
    fun fromList(list: List<Review>): String = JSONArray.toJSONString(list)
}