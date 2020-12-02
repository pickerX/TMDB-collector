package com.me.lib.base.retrofit

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ApiResponseConverterFactory : Converter.Factory() {

    companion object {
        fun create(): ApiResponseConverterFactory = ApiResponseConverterFactory()
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type == ApiResponse::class.java) {
            return ApiResponseConverter()
        }
        return super.responseBodyConverter(type, annotations, retrofit)
    }

    class ApiResponseConverter : Converter<ResponseBody, ApiResponse> {
        override fun convert(value: ResponseBody): ApiResponse? {
            val data = value.charStream().readText()
            val d = Gson().fromJson(data, ApiResponse::class.java)
            return d
        }
    }
}