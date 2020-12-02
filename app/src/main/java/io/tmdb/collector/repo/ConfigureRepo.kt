package io.tmdb.collector.repo

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message
import com.skydoves.sandwich.request
import io.tmdb.collector.api.ConfigureApi
import timber.log.Timber

/**
 *
 * @author: pickerx
 * @date:2020/12/2 3:39 PM
 */
class ConfigureRepo constructor(val api: ConfigureApi) {

    suspend fun getConfigure() {
        api.fetchConfiguration().request { response ->
            when (response) {
                is ApiResponse.Success -> {
                    // stub success case
                    response.data?.let {
                        it.images.poster_sizes
                    }
                }
                is ApiResponse.Failure.Error -> {
                    // stub error case
                    Timber.d(response.message())

                    // handling error based on status code.
                    when (response.statusCode) {
                        StatusCode.InternalServerError -> {
//                            toastLiveData.postValue("InternalServerError")
                        }
                        StatusCode.BadGateway -> {
//                            toastLiveData.postValue("BadGateway")
                        }
                        else -> {
//                            toastLiveData.postValue("$statusCode(${statusCode.code}): ${message()}")
                        }
                    }
                }
                is ApiResponse.Failure.Exception -> {
                    // stub exception case
                }
                else -> {

                }
            }
        }
    }
}