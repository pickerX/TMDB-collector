package io.tmdb.collector.data.http

import com.alibaba.fastjson.annotation.JSONCreator

/**
 * Api success 结果通用类
 *
 * @author: pickerx
 * @date:2020/11/24 2:26 PM
 */
data class ResultData<T> constructor(
    var results: List<T>,
    val page: Int = 1,
    val total_results: Int = 0,
    val total_pages: Int = 1,
    var dates: DateRange? = null
) : BaseResult("success", 0, true) {
    // default constructor for fastJson
    @JSONCreator
    constructor() : this(mutableListOf<T>(), 1, 0, 1)

    data class DateRange(val maximum: String, val minimum: String) {
        // default constructor for fastJson
        @JSONCreator
        constructor() : this("", "")
    }
}