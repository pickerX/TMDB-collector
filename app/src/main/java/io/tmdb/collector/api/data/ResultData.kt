package io.tmdb.collector.api.data

/**
 * Api success 结果通用类
 *
 * @author: pickerx
 * @date:2020/11/24 2:26 PM
 */
data class ResultData<T>(
    val page: Int,
    val results: List<T>,
    val total_results: Int,
    val total_pages: Int
) : BaseResult("", 0, true)