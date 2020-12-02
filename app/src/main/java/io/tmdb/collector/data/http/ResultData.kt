package io.tmdb.collector.data.http

/**
 * Api success 结果通用类
 *
 * @author: pickerx
 * @date:2020/11/24 2:26 PM
 */
data class ResultData<T>(
    val results: List<T>,
    val page: Int? = null,
    val total_results: Int? = null,
    val total_pages: Int? = null,
    var dates: DateRange? = null
) : BaseResult("success", 0, true) {

    data class DateRange(val maximum: String, val minimum: String)
}