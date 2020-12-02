package io.tmdb.collector.api.data

/**
 *
 * @author: pickerx
 * @date:2020/11/24 2:31 PM
 */
open class BaseResult(
    var status_message: String,
    var status_code: Int,
    var success: Boolean = false
)