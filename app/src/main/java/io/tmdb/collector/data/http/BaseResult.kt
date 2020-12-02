package io.tmdb.collector.data.http

/**
 *
 * @author: pickerx
 * @date:2020/11/24 2:31 PM
 */
open class BaseResult(
    var status_message: String = "success",
    var status_code: Int = 0,
    var success: Boolean = true
) {
    fun unknown() {
        onError(-1, "unknown exception")
    }

    fun onError(code: Int, message: String) {
        status_code = code
        status_message = message
        success = false
    }
}