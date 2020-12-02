package com.me.lib.base.retrofit

sealed class ApiResponse(val code: Int, val message: String, val data: Any?) {
}