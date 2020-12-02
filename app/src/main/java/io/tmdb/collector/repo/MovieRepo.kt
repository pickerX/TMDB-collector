package io.tmdb.collector.repo

import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import io.tmdb.collector.api.MovieApi
import io.tmdb.collector.data.Movie
import io.tmdb.collector.data.http.ResultData
import kotlinx.coroutines.flow.flow

/**
 *
 * @author: pickerx
 * @date:2020/11/16 3:12 PM
 */
class MovieRepo constructor(private val movieApi: MovieApi) {

    /**
     * 获取最新的影片
     */
    suspend fun getLatest() = flow<List<Movie>> {

    }

    suspend fun getUpcoming() = flow<ResultData<Movie>> {
        val result = ResultData<Movie>(emptyList())
        movieApi.fetchUpcoming()
            .suspendOnSuccess {
                data?.let {
                    emit(it)
                }
            }
            .suspendOnFailure {
//                if(this is ApiResponse.Failure.Exception<*>) { }
                result.unknown()
                emit(result)
            }
            .suspendOnError {
                result.onError(-1, this.errorBody.toString())
                emit(result)
            }
    }

}